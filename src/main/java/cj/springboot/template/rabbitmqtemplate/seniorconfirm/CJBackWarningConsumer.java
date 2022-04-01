package cj.springboot.template.rabbitmqtemplate.seniorconfirm;

import cj.springboot.template.rabbitmqtemplate.seniorconfirm.config.CJSeniorConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CJBackWarningConsumer {
    @RabbitListener(queues = CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_BACK_QUEUE_NAME)
    public void receiveBackMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("Back_Exchange报警发现不可路由消息： {}", msg);
    }

    @RabbitListener(queues = CJSeniorConfirmConfig.CJ_SENIOR_CONFIRM_BACK_WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("Back_Warning_Exchange报警发现不可路由消息： {}", msg);
    }
}
