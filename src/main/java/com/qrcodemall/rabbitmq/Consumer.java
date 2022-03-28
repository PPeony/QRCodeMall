package com.qrcodemall.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.Property;
import com.qrcodemall.dao.GoodsMapper;
import com.qrcodemall.dao.OrderFormDetailMapper;
import com.qrcodemall.dao.OrderFormMapper;
import com.qrcodemall.entity.Goods;
import com.qrcodemall.entity.OrderForm;
import com.qrcodemall.entity.OrderFormDetail;
import com.qrcodemall.entity.User;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.util.OrderFormNumberGenerator;
import com.rabbitmq.client.Channel;
import lombok.Cleanup;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Peony
 * @Date: 2022/3/22 13:40
 */
@Component
public class Consumer {
    @Autowired
    OrderFormMapper orderFormMapper;

    @Autowired
    OrderFormDetailMapper orderFormDetailMapper;

    @Autowired
    OrderFormService orderFormService;

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    JedisPool jedisPool;

    @RabbitHandler
    @RabbitListener(queues = "queue.hello")
    @Transactional
    public void process(Message message, Channel channel) throws IOException, InterruptedException {
        OrderFormMessage orderFormMessage = JSONObject.parseObject((message.getBody()),
                OrderFormMessage.class);

        Long start = System.currentTimeMillis();
        List<Goods> list = orderFormMessage.getList();
        User user = orderFormMessage.getUser();;
        BigDecimal decimal = getPrice(list);
        OrderForm orderForm = new OrderForm();
        orderForm.setUserId(user.getUserId());
        String id = orderFormMessage.getOrderFormId();
        orderForm.setOrderFormNumber(id);
        orderForm.setOrderFormStatus(0);
        orderForm.setOrderFormPrice(decimal);
        orderForm.setGmtCreated(new Date());
        orderForm.setGmtModified(new Date());
        orderFormService.insertOrderForm(orderForm);
        ////
        OrderForm orderForm2 = orderFormService.selectByOrderFormNumber(id);
        Integer orderFormId = orderForm2.getOrderFormId();
        for (Goods goods : list) {
            OrderFormDetail detail = new OrderFormDetail();
            detail.setOrderFormId(orderFormId);
            detail.setGoodsId(goods.getGoodsId());
            detail.setGoodsName(goods.getGoodsName());
            detail.setGoodsPrice(goods.getGoodsPrice());
            detail.setGoodsQrcodeQuantity(goods.getGoodsQrcodeQuantity());
            detail.setGoodsTypeName(goods.getGoodsTypeName());
            orderFormService.insertOrderFormDetail(detail);
        }
        System.out.println("receive: " + message+"《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        Long end = System.currentTimeMillis();
        System.out.println("consumer cost:"+(end - start));
        //todo,收到回调之后把id对应状态在redis里面更新，0-进行中，1-成功，2-失败
        @Cleanup Jedis jedis = jedisPool.getResource();
        jedis.setex(id, Property.orderFormStatusExpireTime,"1");
    }
    private BigDecimal getPrice(List<Goods> list) {
        BigDecimal decimal = new BigDecimal("0");
        int i = 0;
        for (Goods goods : list) {
            Goods sg = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
            if (sg.getIsDeleted() == 1) {
                GlobalException.fail("第"+i+"个产品不存在");
            }
            decimal = decimal.add(goods.getGoodsPrice());
            i++;
        }
        return decimal;
    }
}
