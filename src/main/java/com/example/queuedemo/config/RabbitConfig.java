package com.example.queuedemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Created by gavin on 2017. 1. 11..
 */
@Configuration
@EnableRabbit
public class RabbitConfig implements RabbitListenerConfigurer {

    //member queue setup
    @Value("${rabbitmq.member.queue}")
    private String MEMBER_QUEUE;
    @Value("${rabbitmq.member.exchange}")
    private String MEMBER_EXCHANGE;
    @Value("${rabbitmq.member.routingkey}")
    private String MEMBER_ROUTING_KEY;



    //member queue bean setup
    @Bean
    Queue memberQueueMessage() {
        return new Queue(MEMBER_QUEUE, true);
    }
    @Bean
    TopicExchange memberExchange() {
        return new TopicExchange(MEMBER_EXCHANGE);
    }
    @Bean
    Binding memberBindingExchangeMessage(Queue memberQueueMessage, TopicExchange memberExchange) {
        return BindingBuilder.bind(memberQueueMessage).to(memberExchange).with(MEMBER_ROUTING_KEY);
    }


    //NOTE: converting and listener config
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(jackson2Converter());
        return factory;
    }

}
