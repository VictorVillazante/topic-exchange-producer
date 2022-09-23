package com.example.topicexchange;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value ="rabbitmq/event")
public class RabbitMQProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange topicExchange;


    @PostMapping
    public String  send(@RequestBody Event event){
        if( event.getName().equalsIgnoreCase("Event A")) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), "report.monthly", event);
        } else if (event.getName().equalsIgnoreCase("Event B")) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), "report.retail.weekly", event);
        } else if (event.getName().equalsIgnoreCase("Event X")) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), "report.business.weekly", event);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"unknown event");
        }
        return "message sent successfully";
    }

}