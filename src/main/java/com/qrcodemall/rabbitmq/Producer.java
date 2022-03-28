package com.qrcodemall.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Peony
 * @Date: 2022/3/22 13:32
 */
@Component
public class Producer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    private RabbitTemplate rabbitTemplate;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        OrderFormMessage msg = JSONObject.parseObject(correlationData.getReturnedMessage().getBody(), OrderFormMessage.class);
        if(ack){
            System.out.println("消息发送成功:"+msg+" "+ack+" "+cause);
        }else{
            System.out.println("消息发送失败:"+msg+" "+ack+" "+cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息丢失:exchange"+exchange+" "+routingKey+" "+replyCode+" "+replyText+" "+message);
    }
}
