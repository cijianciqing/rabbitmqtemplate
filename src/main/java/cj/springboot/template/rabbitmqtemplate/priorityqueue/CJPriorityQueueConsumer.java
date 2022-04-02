package cj.springboot.template.rabbitmqtemplate.priorityqueue;

import cj.springboot.template.rabbitmqtemplate.seniorconfirm.config.CJBackExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class CJPriorityQueueConsumer {
    @RabbitListener(queues = CJPriorityQueueConfig.CJ_PRIORITY_QUEUE_QUEUE_NAME)
    public void receiveBackMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("CJPriorityQueueConsumer收到消息： {}", msg);
    }

}
