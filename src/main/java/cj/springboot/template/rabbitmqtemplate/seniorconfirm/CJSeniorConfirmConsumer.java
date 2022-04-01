package cj.springboot.template.rabbitmqtemplate.seniorconfirm;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Slf4j
@Component
public class CJSeniorConfirmConsumer {


    @RabbitListener(queues = CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前seniorConfirm测试----时间： {},收到延迟队列信息{}", new Date().toString(), msg);
    }
}