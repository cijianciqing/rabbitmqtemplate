package cj.springboot.template.rabbitmqtemplate.seniorconfirm;

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
@RequestMapping("/seniorConfirm")
//@RestController
public class CJSeniorConfirmProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CJRabbitConfirmCallback cjRabbitConfirmCallback;
    @Autowired
    private CJRabbitConfirmReturnCallback cjRabbitConfirmReturnCallback;
    //依赖注入 rabbitTemplate 之后再设置它的回调对象
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(cjRabbitConfirmCallback);
        rabbitTemplate.setReturnCallback(cjRabbitConfirmReturnCallback);
    }

    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsgOptimize/{message}")
    public void sendMsgOptimize(@PathVariable String message){
        log.info("Senior Confirm当前时间： {},发送一条信息给两个 routing-key :{}", new Date(), message);
        //指定消息 id 为 1
        CorrelationData correlationData1=new CorrelationData("1");

        CorrelationData correlationData2=new CorrelationData("2");

        rabbitTemplate.convertAndSend(CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_EXCHANGE_NAME,
                CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_BINDING_KEY,message,correlationData1);

        //此次发送的消息 routingkey设置有问题， rabbit-server端的交换机没有对应的配置
        rabbitTemplate.convertAndSend(CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_EXCHANGE_NAME,
                CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_BINDING_KEY+"bad",message,correlationData2);
    }
}
