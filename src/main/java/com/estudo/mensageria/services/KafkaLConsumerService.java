package com.estudo.mensageria.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaLConsumerService {

    public static final String RECEIVED_MESSAGE = "Received Message: {}";

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaLConsumerService.class);

    @KafkaListener(topics = "orders", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public  void listenOrders(@Payload String message,@Headers Acknowledgment ack){
        logger.info(RECEIVED_MESSAGE,message );
        orderService.saveOrderFromKafka(message);
        ack.acknowledge();
    }

    @KafkaListener(topics = "payments", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public  void listenPayments(@Payload String message,@Headers Acknowledgment ack){
        logger.info(RECEIVED_MESSAGE,message );
        paymentService.savePaymentFromKafka(message);
        ack.acknowledge();
    }

}
