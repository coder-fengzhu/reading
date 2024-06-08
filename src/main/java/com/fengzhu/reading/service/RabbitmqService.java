package com.fengzhu.reading.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class RabbitmqService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.reading.exchange}")
    private String exchange;

    @Value("${rabbitmq.reading.routingkey}")
    private String routingkey;

    public <T> void publishMessage(T type){
        amqpTemplate.convertAndSend(exchange, routingkey, type);
    }

}
