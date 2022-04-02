package cj.springboot.template.rabbitmqtemplate.test.basic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CJRabbitMQBasicConfig {

    public static final String CJ_EXCHANGE_NAME = "cj_rabbitmq_basic_exchange";
    public static final String CJ_ROUTING_KEY = "cj_rabbitmq_basic_routingkey";

    public static final String CJ_QUEUE_NAME = "cj_rabbitmq_basic_queue";


    public static final String CJ_TOPIC_EXCHANGE_NAME = "cj_basic_topic_exchange";
    public static final String CJ_DIRECT_EXCHANGE_NAME = "cj_basic_direct_exchange";
    public static final String CJ_FANOUT_EXCHANGE_NAME = "cj_basic_fanout_exchange";


    public static final String CJ_QUEUE_NAME1 = "cj_rabbitmq_basic_queue1";
    // 声明 topic  Exchange
    @Bean("cjBasicTopicExchange")
    public TopicExchange cjBasicTopicExchange() {
        return new TopicExchange(CJ_TOPIC_EXCHANGE_NAME);
    }
    // 声明 direct  Exchange
    @Bean("cjBasicDirectExchange")
    public DirectExchange cjBasicDirectExchange() {
        return new DirectExchange(CJ_DIRECT_EXCHANGE_NAME);
    }
    // 声明 fanout  Exchange
    @Bean("cjBasicFanoutExchange")
    public FanoutExchange cjBasicFanoutExchange() {
        return new FanoutExchange(CJ_FANOUT_EXCHANGE_NAME);
    }



    //声明 Queue
    @Bean("cjBasicQueue")
    public Queue cjbasicQueue() {
        return QueueBuilder
                .durable(CJ_QUEUE_NAME)
                .build();
    }
    //声明 Queue
    @Bean("cjbasicQueue1")
    public Queue cjbasicQueue1() {
        return QueueBuilder
                .durable(CJ_QUEUE_NAME1)
                .build();
    }
    // 绑定 bk queue
//    @Bean
//    public Binding cjBasicBinding(@Qualifier("cjBasicQueue") Queue cjBasicQueue,
//                                              @Qualifier("cjBasicFanoutExchange") FanoutExchange cjBasicFanoutExchange) {
//        return BindingBuilder.bind(cjBasicQueue).to(cjBasicFanoutExchange);
//    }
//    @Bean
//    public Binding cjBasicBinding1(@Qualifier("cjbasicQueue1") Queue cjbasicQueue1,
//                                  @Qualifier("cjBasicFanoutExchange") FanoutExchange cjBasicFanoutExchange) {
//        return BindingBuilder.bind(cjbasicQueue1).to(cjBasicFanoutExchange);
//    }

    @Bean
    public Binding cjBasicBinding(@Qualifier("cjBasicQueue") Queue cjBasicQueue,
                                  @Qualifier("cjBasicDirectExchange") DirectExchange cjBasicDirectExchange) {
        return BindingBuilder.bind(cjBasicQueue).to(cjBasicDirectExchange).with(CJ_ROUTING_KEY);
    }
    @Bean
    public Binding cjBasicBinding1(@Qualifier("cjbasicQueue1") Queue cjbasicQueue1,
                                   @Qualifier("cjBasicDirectExchange") DirectExchange cjBasicDirectExchange) {
        return BindingBuilder.bind(cjbasicQueue1).to(cjBasicDirectExchange).with(CJ_ROUTING_KEY);
    }

}
