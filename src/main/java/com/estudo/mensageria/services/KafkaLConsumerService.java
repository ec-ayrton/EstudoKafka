package com.estudo.mensageria.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaLConsumerService {

    public static final String RECEIVED_MESSAGE = "Received Message: {}";

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaLConsumerService.class);

    @KafkaListener(topics = "orders", groupId = "my-group")
    public  void listenOrders(String message){
        logger.info(RECEIVED_MESSAGE,message );
        orderService.saveOrderFromKafka(message);
    }

    @KafkaListener(topics = "payments", groupId = "my-group")
    public  void listenPayments(String message){
        logger.info(RECEIVED_MESSAGE,message );
        paymentService.savePaymentFromKafka(message);
    }

}
