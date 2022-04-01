package cj.springboot.template.rabbitmqtemplate.seniorconfirm;

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

    // 声明 xExchange
    @Bean("cjSeniorConfirmExchange")
    public DirectExchange cjSeniorConfirmExchange() {
        return new DirectExchange(CJ_SENIOR_CONFIRM_EXCHANGE_NAME);
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