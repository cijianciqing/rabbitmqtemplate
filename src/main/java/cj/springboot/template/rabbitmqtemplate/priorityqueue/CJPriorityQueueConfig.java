package cj.springboot.template.rabbitmqtemplate.priorityqueue;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
public class CJPriorityQueueConfig {

    public static final String CJ_PRIORITY_QUEUE_EXCHANGE_NAME = "cjpriorityqueue_exchange";
    public static final String CJ_PRIORITY_QUEUE_QUEUE_NAME = "cjpriorityqueue_queue";
    public static final String CJ_PRIORITY_QUEUE_ROUTING_KEY = "cjpriorityqueue_key";
    // 声明 PriorityQueue Exchange
    @Bean("cjPriorityQueueExchange")
    public FanoutExchange cjPriorityQueueExchange() {
        return new FanoutExchange(CJ_PRIORITY_QUEUE_EXCHANGE_NAME);
    }



    //声明PriorityQueue 队列
    @Bean("cjPriorityQueueQueue")
    public Queue cjPriorityQueueQueue() {

        return QueueBuilder.durable(CJ_PRIORITY_QUEUE_QUEUE_NAME)
                .maxPriority(13)//设置队列最大优先级
                .build();
    }



    // 绑定 bk queue
    @Bean
    public Binding cjSeniorConfirmBackBinding(@Qualifier("cjPriorityQueueQueue") Queue cjPriorityQueueQueue,
                                              @Qualifier("cjPriorityQueueExchange") FanoutExchange cjPriorityQueueExchange) {
        return BindingBuilder.bind(cjPriorityQueueQueue).to(cjPriorityQueueExchange);
    }

}
