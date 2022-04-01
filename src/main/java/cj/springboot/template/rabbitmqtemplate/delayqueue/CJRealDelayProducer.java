package cj.springboot.template.rabbitmqtemplate.delayqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/realdelayqueue")
@RestController
public class CJRealDelayProducer {

    public static final String CJ_DELAY_EXCHANGE_NAME = "cj..rabbitmq.exchange.delay";
    public static final String CJ_DELAY_QUEUE_NAME = "cj.rabbitmq.queue.delay";
    public static final String CJ_DELAY_BINDING_KEY = "cj.rabbitmq.queue.delay";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsgOptimize/{message}/{ttl}")
    public void sendMsgOptimize(@PathVariable String message, @PathVariable Integer ttl){
        log.info("当前时间： {},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend(CJ_DELAY_EXCHANGE_NAME, CJ_DELAY_BINDING_KEY, "Produce设置ttl的消息: "+message, (messageP) ->{
            //为每个消息设置
            messageP.getMessageProperties().setDelay(ttl);
            return messageP;
        });

    }
}
