package cj.springboot.template.rabbitmqtemplate.test.basic.consumer;

import cj.springboot.template.rabbitmqtemplate.test.basic.CJRabbitMQBasicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class CJRabbitMQBasicConsumer2 {
    @RabbitListener(queues = CJRabbitMQBasicConfig.CJ_QUEUE_NAME)
    public void receiveBackMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("CJRabbitMQBasicConsumer2 收到消息： {}，时间： {}", msg, new Date());
    }

}
