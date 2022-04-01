package cj.springboot.template.rabbitmqtemplate.deathletter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RequestMapping("/delayqueue")
@RestController
public class CJDelayQueueProviderController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){log.info("当前时间： {},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: "+message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: "+message);
    }

    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsgOptimize/{message}/{ttl}")
    public void sendMsgOptimize(@PathVariable String message,@PathVariable String ttl){
        log.info("当前时间： {},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XC", "Produce设置ttl的消息: "+message, (messageP) ->{
            //为每个消息设置
            messageP.getMessageProperties().setExpiration(ttl);
            return messageP;
        });

    }
}