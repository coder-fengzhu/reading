package com.fengzhu.reading.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.reading.queue}")
    private String queueName;

    @Value("${rabbitmq.reading.exchange}")
    private String exchange;

    @Value("${rabbitmq.reading.routingkey}")
    private String routingkey;

    @Value("${rabbitmq.reading.dlq.queue}")
    private String dlqQueueName;

    @Value("${rabbitmq.reading.dlq.exchange}")
    private String dlqExchange;

    @Value("${rabbitmq.reading.dlq.routingkey}")
    private String dlqRoutingkey;

    @Bean
    Queue queue() {
        Map<String,Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", dlqExchange);
        params.put("x-dead-letter-routing-key", dlqRoutingkey);
        return QueueBuilder.durable(queueName).withArguments(params).build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    Queue dlqQueue() {
        return new Queue(dlqQueueName);
    }

    @Bean
    public DirectExchange dlqExchange(){
        return new DirectExchange(dlqExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Binding dlqBinding(Queue dlqQueue, DirectExchange dlqExchange) {
        return BindingBuilder.bind(dlqQueue).to(dlqExchange).with(dlqRoutingkey);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

}
