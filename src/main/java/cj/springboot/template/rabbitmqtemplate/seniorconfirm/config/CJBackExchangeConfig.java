package cj.springboot.template.rabbitmqtemplate.seniorconfirm.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class CJBackExchangeConfig {
    /*
     * Back Exchange config---start
     * */
    public static final String CJ_SENIOR_CONFIRM_BACK_EXCHANGE_NAME = "cjbackexchange";
    public static final String CJ_SENIOR_CONFIRM_BACK_QUEUE_NAME = "cj.queue.seniorconfirm.back";
    public static final String CJ_SENIOR_CONFIRM_BACK_WARNING_QUEUE_NAME = "cj.binding.seniorconfirm.warning";

    // 声明 back Exchange
    @Bean("cjSeniorConfirmBackExchange")
    public FanoutExchange cjSeniorConfirmBackExchange() {
        return new FanoutExchange(CJ_SENIOR_CONFIRM_BACK_EXCHANGE_NAME);
    }


    //声明back队列
    @Bean("cjSeniorConfirmBackQueue")
    public Queue cjSeniorConfirmBackQueue() {
        return QueueBuilder.durable(CJ_SENIOR_CONFIRM_BACK_QUEUE_NAME).build();
    }

    //声明warning队列
    @Bean("cjSeniorConfirmBackWarningQueue")
    public Queue cjSeniorConfirmBackWarningQueue() {
        return QueueBuilder.durable(CJ_SENIOR_CONFIRM_BACK_WARNING_QUEUE_NAME).build();
    }

    // 绑定 bk queue
    @Bean
    public Binding cjSeniorConfirmBackBinding(@Qualifier("cjSeniorConfirmBackQueue") Queue cjSeniorConfirmBackQueue,
                                              @Qualifier("cjSeniorConfirmBackExchange") FanoutExchange cjSeniorConfirmBackExchange) {
        return BindingBuilder.bind(cjSeniorConfirmBackQueue).to(cjSeniorConfirmBackExchange);
    }
    // 绑定 bk_warning queue
    @Bean
    public Binding cjSeniorConfirmBackWarningBinding(@Qualifier("cjSeniorConfirmBackWarningQueue") Queue cjSeniorConfirmBackWarningQueue,
                                                     @Qualifier("cjSeniorConfirmBackExchange") FanoutExchange cjSeniorConfirmBackExchange) {
        return BindingBuilder.bind(cjSeniorConfirmBackWarningQueue).to(cjSeniorConfirmBackExchange);
    }
    /*
     * Back Exchange config---END
     * */
}
