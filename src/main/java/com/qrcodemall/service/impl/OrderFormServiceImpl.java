package com.qrcodemall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrcodemall.common.Exception.GlobalException;
import com.qrcodemall.common.PageProperty;
import com.qrcodemall.common.Property;
import com.qrcodemall.controller.vo.PromotionGoodsVO;
import com.qrcodemall.dao.GoodsMapper;
import com.qrcodemall.dao.OrderFormDetailMapper;
import com.qrcodemall.dao.OrderFormMapper;
import com.qrcodemall.entity.*;
import com.qrcodemall.rabbitmq.OrderFormMessage;
import com.qrcodemall.rabbitmq.Producer;
import com.qrcodemall.service.OrderFormService;
import com.qrcodemall.util.JedisUtil;
import com.qrcodemall.util.OrderFormNumberGenerator;
import com.qrcodemall.util.PageUtil;
import lombok.Cleanup;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Peony
 * @Date: 2022/3/7 12:03
 */
@Service
public class OrderFormServiceImpl implements OrderFormService {

    @Autowired
    OrderFormMapper orderFormMapper;

    @Autowired
    OrderFormDetailMapper orderFormDetailMapper;

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    Producer producer;
    @Autowired
    JedisUtil jedisUtil;

    @Override
    public PageInfo<OrderForm> selectOrderForm(Integer userId, Date beginTime, Date endTime, Integer pageNum,
                                               Integer orderFormStatus) {
        OrderForm orderForm = new OrderForm();
        orderForm.setUserId(userId);
        orderForm.setOrderFormStatus(orderFormStatus);
        return PageUtil.generatePageInfoByTime(orderForm,pageNum,beginTime,endTime,orderFormMapper);
    }

    @Override
    public PageInfo<OrderForm> selectAllOrderForms(Date beginTime, Date enTime, Integer pageNum) {
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(0);
        return PageUtil.generatePageInfoByTime(orderForm,pageNum,beginTime,enTime,orderFormMapper);
    }

    @Override
    public BigDecimal getSalesSituation(Date beginTime, Date endtime) {
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(0);
        orderForm.setOrderFormStatus(1);
        List<OrderForm> list = orderFormMapper.selectByEntityAndTime(orderForm,beginTime,endtime);
        BigDecimal res = new BigDecimal("0.0");
        for (OrderForm form : list) {
            res = res.add(form.getOrderFormPrice());
        }
        return res;
    }

    @Override
    public OrderForm selectByOrderFormNumber(String orderFormNumber) {
        OrderFormExample example = new OrderFormExample();
        OrderFormExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andOrderFormNumberEqualTo(orderFormNumber);
        List<OrderForm> list = orderFormMapper.selectByExample(example);
        if (list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    public String generateOrderForm(List<Goods> list, User user,Integer promotionId){
        /*
        BigDecimal decimal = getPrice(list);
        OrderForm orderForm = new OrderForm();
        orderForm.setUserId(user.getUserId());
        String id = OrderFormNumberGenerator.generate();
        orderForm.setOrderFormNumber(id);
        orderForm.setOrderFormStatus(0);
        orderForm.setOrderFormPrice(decimal);
        orderForm.setGmtCreated(new Date());
        orderForm.setGmtModified(new Date());
        insertOrderForm(orderForm);
        ////
        OrderForm orderForm2 = selectByOrderFormNumber(id);
        Integer orderFormId = orderForm2.getOrderFormId();
        for (Goods goods : list) {
            OrderFormDetail detail = new OrderFormDetail();
            detail.setOrderFormId(orderFormId);
            detail.setGoodsId(goods.getGoodsId());
            detail.setGoodsName(goods.getGoodsName());
            detail.setGoodsPrice(goods.getGoodsPrice());
            detail.setGoodsQrcodeQuantity(goods.getGoodsQrcodeQuantity());
            detail.setGoodsTypeName(goods.getGoodsTypeName());
            insertOrderFormDetail(detail);
        }

         */
//        return orderForm2;
        //promotionId如果不为null，说明是促销商品，需要进行redis操作,redis有加锁写操作
        Jedis jedis = null;
        try {
            jedis = jedisUtil.getJedis();
            if (promotionId != null) {
                String key = Property.promotionRedisKeyPrefix+String.valueOf(promotionId);
                Integer count = Integer.valueOf(jedis.get(key));
                if (count <= 0) {
                    return "sold out";
                }
                jedis.decr(key);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (jedis != null) jedis.close();
        }
        String orderFormId = OrderFormNumberGenerator.generate();
        try {
            OrderFormMessage message = new OrderFormMessage(orderFormId,list, user);
            String uid = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uid);
            String msg = JSON.toJSONString(message);
            Message returnedMessage = MessageBuilder.withBody(msg.getBytes()).build();
            correlationId.setReturnedMessage(returnedMessage);
            System.out.println("Sender : " + message);
            //convertAndSend(exchange:交换机名称,routingKey:路由关键字,object:发送的消息内容,correlationData:消息ID)
            producer.getRabbitTemplate().convertAndSend("exchange.hello","helloKey", msg,correlationId);
            jedis.setex(orderFormId, Property.orderFormStatusExpireTime,"0");
        } catch (Exception e) {
            jedis.setex(orderFormId,Property.orderFormStatusExpireTime,"2");
        }
        return orderFormId;
    }

    @Override
    public Integer buyingSuccessfully(String orderFormNumber) {
        OrderFormExample example = new OrderFormExample();
        OrderFormExample.Criteria criteria = example.createCriteria();
        criteria.andOrderFormNumberEqualTo(orderFormNumber);
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderFormStatus(1);
        orderForm.setGmtModified(new Date());
        orderFormMapper.updateByExampleSelective(orderForm,example);
        return 1;
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

    @Override
    public List<OrderFormDetail> selectOrderFormDetailWithoutPage(Integer orderFormId) {
        OrderFormDetailExample example = new OrderFormDetailExample();
        OrderFormDetailExample.Criteria criteria = example.createCriteria();
        criteria.andOrderFormIdEqualTo(orderFormId);
        return orderFormDetailMapper.selectByExample(example);

    }

    @Override
    public Integer updateOrderForm(OrderForm orderForm) {
        orderForm.setGmtModified(new Date());
        return orderFormMapper.updateByPrimaryKeySelective(orderForm);
    }

    @Override
    public Integer insertOrderForm(OrderForm orderForm) {
        orderForm.setGmtModified(new Date());
        orderForm.setIsDeleted(0);
        orderForm.setGmtModified(new Date());
        return orderFormMapper.insertSelective(orderForm);
    }

    @Override
    public Integer deleteOrderForm(Integer orderFormId) {
        //同时删除detail
        OrderForm orderForm = new OrderForm();
        orderForm.setIsDeleted(1);
        orderForm.setOrderFormId(orderFormId);
        orderForm.setGmtModified(new Date());
        orderFormMapper.updateByPrimaryKeySelective(orderForm);
        OrderFormDetailExample example = new OrderFormDetailExample();
        OrderFormDetailExample.Criteria criteria = example.createCriteria();
        criteria.andOrderFormIdEqualTo(orderFormId);
        OrderFormDetail orderFormDetail = new OrderFormDetail();
        orderFormDetail.setIsDeleted(1);
        orderFormDetail.setGmtModified(new Date());
        orderFormDetailMapper.updateByExampleSelective(orderFormDetail,example);
        return 1;
    }


    @Override
    public PageInfo<OrderFormDetail> selectOrderFormDetails(Integer orderFormId, Integer pageNum) {
        OrderFormDetailExample orderFormDetail = new OrderFormDetailExample();
        OrderFormDetailExample.Criteria criteria = orderFormDetail.createCriteria();
        criteria.andOrderFormIdEqualTo(orderFormId);
        criteria.andIsDeletedEqualTo(0);
        PageHelper.startPage(pageNum, PageProperty.PAGESIZE);
        List<OrderFormDetail> list = orderFormDetailMapper.selectByExample(orderFormDetail);
        return new PageInfo<>(list);
    }

    @Override
    public Integer insertOrderFormDetail(OrderFormDetail orderFormDetail) {
        orderFormDetail.setGmtCreated(new Date());
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.insertSelective(orderFormDetail);
    }

    @Override
    public Integer updateOrderFormDetail(OrderFormDetail orderFormDetail) {
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.updateByPrimaryKeySelective(orderFormDetail);
    }

    @Override
    public Integer deleteOrderFormDetail(Integer orderFormDetailId) {
        OrderFormDetail orderFormDetail = new OrderFormDetail();
        orderFormDetail.setIsDeleted(1);
        orderFormDetail.setOrderFormDeatilId(orderFormDetailId);
        orderFormDetail.setGmtModified(new Date());
        return orderFormDetailMapper.updateByPrimaryKeySelective(orderFormDetail);
    }
}
