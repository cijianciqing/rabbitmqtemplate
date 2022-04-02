package cj.springboot.template.rabbitmqtemplate.test.basic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CJRabbitMQBasicConfig {

    public static final String CJ_EXCHANGE_NAME = "cjpriorityqueue_exchange";
    public static final String CJ_ROUTING_KEY = "cjpriorityqueue_key";

    public static final String CJ_QUEUE_NAME = "cjpriorityqueue_queue";

    // 声明  Exchange
    @Bean("cjBasicExchange")
    public TopicExchange cjBasicExchange() {
        return new TopicExchange(CJ_EXCHANGE_NAME);
    }

    //声明 Queue
    @Bean("cjBasicQueue")
    public Queue cjPriorityQueueQueue() {
        return QueueBuilder
                .durable(CJ_QUEUE_NAME)
                .build();
    }

    // 绑定 bk queue
    @Bean
    public Binding cjBasicBinding(@Qualifier("cjBasicQueue") Queue cjBasicQueue,
                                              @Qualifier("cjBasicExchange") TopicExchange cjBasicExchange) {
        return BindingBuilder.bind(cjBasicQueue).to(cjBasicExchange).with(CJ_ROUTING_KEY);
    }

}
