package com.fyp.fly.services.common.config;

import com.fyp.fly.services.common.consumer.RabbitMessageListener;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fyp.fly.common.event.FlyEvent.SERVICE_COMMON_EXCHANGE;
import static com.fyp.fly.common.event.FlyEvent.SERVICE_COUNT_TOPIC;

@Configuration
public class RabbitConfig {

   private final static String SERVICE_COUNT_CHANGED_QUEUE = "service-common-message-queue";

    @Bean
    Queue queue() {
        return new Queue(SERVICE_COUNT_CHANGED_QUEUE, true);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(SERVICE_COMMON_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue,TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SERVICE_COUNT_TOPIC);
    }

    @Bean
    SimpleMessageListenerContainer container(RabbitMessageListener messageListener, ConnectionFactory factory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(SERVICE_COUNT_CHANGED_QUEUE);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(messageListener);
        return container;
    }
}
