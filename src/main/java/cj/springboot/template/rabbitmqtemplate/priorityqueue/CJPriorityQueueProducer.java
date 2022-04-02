package cj.springboot.template.rabbitmqtemplate.priorityqueue;

import cj.springboot.template.rabbitmqtemplate.seniorconfirm.config.CJRabbitConfirmCallback;
import cj.springboot.template.rabbitmqtemplate.seniorconfirm.config.CJRabbitConfirmReturnCallback;
import cj.springboot.template.rabbitmqtemplate.seniorconfirm.config.CJSeniorConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@RequestMapping("/priorityqueue")
@RestController
public class CJPriorityQueueProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("CJPriorityQueueProducer当前时间： {},发送一条信息:{}", new Date(), message);

        rabbitTemplate.convertAndSend(CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_EXCHANGE_NAME,
                CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_ROUTING_KEY, message+"1", msg -> {
                    msg.getMessageProperties().setPriority(3);//设置消息队列优先级
                    return msg;
                });

        rabbitTemplate.convertAndSend(CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_EXCHANGE_NAME,
                CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_ROUTING_KEY, message+"2", msg -> {
                    msg.getMessageProperties().setPriority(4);//设置消息队列优先级
                    return msg;
                });

        rabbitTemplate.convertAndSend(CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_EXCHANGE_NAME,
                CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_ROUTING_KEY, message+"3", msg -> {
                    msg.getMessageProperties().setPriority(2);//设置消息队列优先级
                    return msg;
                });


    }
}
