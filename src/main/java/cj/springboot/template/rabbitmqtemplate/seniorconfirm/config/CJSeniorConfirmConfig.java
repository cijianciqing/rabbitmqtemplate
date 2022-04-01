package cj.springboot.template.rabbitmqtemplate.seniorconfirm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CJSeniorConfirmConfig {


    public static final String CJ_SENIOR_CONFIRM_EXCHANGE_NAME = "cj.rabbitmq.exchange.seniorconfirm";
    public static final String CJ_SENIOR_CONFIRM_QUEUE_NAME = "cj.rabbitmq.queue.seniorconfirm";
    public static final String CJ_SENIOR_CONFIRM_BINDING_KEY = "cj.rabbitmq.binding.seniorconfirm";

    // 声明 confirm Exchange
    @Bean("cjSeniorConfirmExchange")
    public DirectExchange cjSeniorConfirmExchange() {
//        Map<String,Object> args = new HashMap<>();
        //为此Exchange配置 备份交换机
//        args.put("alternate-exchange", CJBackExchangeConfig.CJ_SENIOR_CONFIRM_BACK_EXCHANGE_NAME);

       return ExchangeBuilder.directExchange(CJ_SENIOR_CONFIRM_EXCHANGE_NAME)
               .durable(true)
               .withArgument("alternate-exchange", CJBackExchangeConfig.CJ_SENIOR_CONFIRM_BACK_EXCHANGE_NAME)
               .build();
    }







    //声明队列
    @Bean("cjSeniorConfirmQueue")
    public Queue cjSeniorConfirmQueue() {
        return QueueBuilder.durable(CJ_SENIOR_CONFIRM_QUEUE_NAME).build();
    }

    // 绑定
    @Bean("cjSeniorConfirmBinding")
    public Binding CJ_SENIOR_CONFIRM_BINDING_KEY(@Qualifier("cjSeniorConfirmQueue") Queue cjSeniorConfirmQueue,
                                                 @Qualifier("cjSeniorConfirmExchange") DirectExchange cjSeniorConfirmExchange) {
        return BindingBuilder.bind(cjSeniorConfirmQueue).to(cjSeniorConfirmExchange).with(CJ_SENIOR_CONFIRM_BINDING_KEY);
    }




}