package com.wiiee.core.platform.config;

/**
 * Created by wiiee on 9/10/2017.
 */
//@Configuration
public class LogMqConfig {
//    public final static String QUEUE_NAME = "trip-log";
//
//    @Bean
//    Queue queue() {
//        return new Queue(QUEUE_NAME, false);
//    }
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("trip-log-exchange");
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(LogReceiver logReceiver) {
//        return new MessageListenerAdapter(logReceiver, "receive");
//    }
}
