package cj.springboot.template.rabbitmqtemplate.seniorconfirm;

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
@RequestMapping("/seniorConfirm")
@RestController
public class CJSeniorConfirmProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CJRabbitConfirmCallback cjRabbitConfirmCallback;
    //依赖注入 rabbitTemplate 之后再设置它的回调对象
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(cjRabbitConfirmCallback);
    }

    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsgOptimize/{message}")
    public void sendMsgOptimize(@PathVariable String message){
        log.info("当前时间： {},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        //指定消息 id 为 1
        CorrelationData correlationData1=new CorrelationData("1");

        rabbitTemplate.convertAndSend(CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_EXCHANGE_NAME,
                CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_BINDING_KEY,message,correlationData1);
    }
}
