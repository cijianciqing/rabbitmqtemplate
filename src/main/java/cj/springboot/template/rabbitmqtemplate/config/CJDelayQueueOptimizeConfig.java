package cj.springboot.template.rabbitmqtemplate.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CJDelayQueueOptimizeConfig {
    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_C = "QC";

    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String DEAD_LETTER_QUEUE = "QD";

    @Bean("queueC")
    public Queue queue(){
        Map<String, Object> args = new HashMap<>(2);
//声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
//声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "YD");
//声明队列的 TTL
//        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    // 声明队列C  绑定 X 交换机
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


}