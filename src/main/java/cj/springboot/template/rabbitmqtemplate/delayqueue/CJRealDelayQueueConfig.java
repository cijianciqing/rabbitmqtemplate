package cj.springboot.template.rabbitmqtemplate.delayqueue;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CJRealDelayQueueConfig {
    public static final String CJ_DELAY_EXCHANGE_NAME = "cj..rabbitmq.exchange.delay";
    public static final String CJ_DELAY_QUEUE_NAME = "cj.rabbitmq.queue.delay";
    public static final String CJ_DELAY_BINDING_KEY = "cj.rabbitmq.queue.delay";
    // 声明 xExchange
    @Bean("cjDealyExchange")
    public CustomExchange cjDealyExchange(){
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型
        args.put("x-delayed-type", "direct");//转送 模式 ？
        return new CustomExchange(CJ_DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,
        args);
    }

    //声明延迟队列
    @Bean("cjDelayQueue")
    public Queue cjDelayQueue(){
        return QueueBuilder.durable(CJ_DELAY_QUEUE_NAME).build();
    }

    // 绑定
    @Bean("cjDelayBinding")
    public Binding cjDelayBinding(@Qualifier("cjDelayQueue") Queue cjDelayQueue,
                                  @Qualifier("cjDealyExchange") CustomExchange cjDealyExchange){
        return BindingBuilder.bind(cjDelayQueue).to(cjDealyExchange).with(CJ_DELAY_BINDING_KEY).noargs();
    }



}