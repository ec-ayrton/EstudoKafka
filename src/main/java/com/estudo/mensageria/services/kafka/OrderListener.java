package com.estudo.mensageria.services.kafka;

import com.estudo.mensageria.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    public static final String RECEIVED_MESSAGE = "Received Message: {}";

    @KafkaListener(topics = "orders", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public  void listenOrders(@Payload String message){
        logger.info(RECEIVED_MESSAGE,message );
        orderService.saveOrderFromKafka(message);
    }
}
