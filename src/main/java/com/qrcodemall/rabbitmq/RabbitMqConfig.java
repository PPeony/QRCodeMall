package com.qrcodemall.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Peony
 * @Date: 2022/3/22 13:26
 */
@Configuration
public class RabbitMqConfig {
    private static Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);
    @Autowired
    private CachingConnectionFactory connectionFactory;

    //目标转换器，需要哪种类型的转换器就创建哪种类型的转换器
    @Bean
    public DirectExchange exchangeHello(){
        Map<String, Object> eArguments = new HashMap<>();
        //备份交换器参数
        eArguments.put("alternate-exchange", "exchange.ae");
        return new DirectExchange("exchange.hello",true,false,eArguments);
    }
    //备份转换器
    @Bean
    public FanoutExchange exchangeAE(){
        return new FanoutExchange("exchange.ae",true,false,null);
    }
    //死信转换器
    @Bean
    public TopicExchange exchangeDLX(){
        return new TopicExchange("exchange.dlx",true,false,null);
    }

    //目标对列
    @Bean
    public Queue queueHello() {
        Map<String, Object> args = new HashMap<>();
        //声明死信交换器
        args.put("x-dead-letter-exchange", "exchange.dlx");
        //声明死信路由键
        args.put("x-dead-letter-routing-key", "dlx.test" );
        //声明队列消息过期时间 5000ms
        args.put("x-message-ttl", 5000);
        return new Queue("queue.hello",true,false,false,args);
    }

    //备份对列
    @Bean
    public Queue queueAE() {
        return new Queue("queue.ae",true,false,false,null);
    }

    //死信对列
    @Bean
    public Queue queueDLX() {
        return new Queue("queue.dlx",true,false,false,null);
    }

    //绑定目标对列
    @Bean
    public Binding bindingExchangeDirect(@Qualifier("queueHello")Queue queueHello, @Qualifier("exchangeHello") DirectExchange exchangeHello){
        return  BindingBuilder.bind(queueHello).to(exchangeHello).with("helloKey");
    }
    //绑定备份对列
    @Bean
    public Binding bindingExchangeAE(@Qualifier("queueAE")Queue queueAE, @Qualifier("exchangeAE") FanoutExchange exchangeAE){
        return  BindingBuilder.bind(queueAE).to(exchangeAE);
    }

    //绑定死信对列
    @Bean
    public Binding bindingExchangeDLX(@Qualifier("queueDLX")Queue queueAE, @Qualifier("exchangeDLX") TopicExchange exchangeDLX){
        return  BindingBuilder.bind(queueAE).to(exchangeDLX).with("dlx.*");
    }

    /**
     * 如果需要在生产者需要消息发送后的回调，
     * 需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，
     * 如果rabbitTemplate设置为单例bean，
     * 则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
}
