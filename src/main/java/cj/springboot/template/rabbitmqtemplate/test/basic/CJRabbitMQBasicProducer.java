package cj.springboot.template.rabbitmqtemplate.test.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/rabbitmqBasic")
@RestController
public class CJRabbitMQBasicProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /*
     * 7.6.1 延迟队列优化配置
     * */
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("CJRabbitMQBasicProducer发送一条信息: {}, 发送时间： {},", message, new Date());

        rabbitTemplate.convertAndSend(CJRabbitMQBasicConfig.CJ_EXCHANGE_NAME,
                CJRabbitMQBasicConfig.CJ_ROUTING_KEY, message);
    }
}
