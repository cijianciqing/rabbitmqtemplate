package cj.springboot.template.rabbitmqtemplate.delayqueue;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Slf4j
@Component
public class CJRealDelayQueueConsumer {

    public static final String CJ_DELAY_EXCHANGE_NAME = "cj..rabbitmq.exchange.delay";
    public static final String CJ_DELAY_QUEUE_NAME = "cj.rabbitmq.queue.delay";
    public static final String CJ_DELAY_BINDING_KEY = "cj.rabbitmq.queue.delay";

    @RabbitListener(queues = CJ_DELAY_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间： {},收到死信队列信息{}", new Date().toString(), msg);
    }
}