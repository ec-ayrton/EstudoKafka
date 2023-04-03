package com.estudo.mensageria.services.kafka;

import com.estudo.mensageria.exceptions.ProcessingException;
import com.estudo.mensageria.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentListener.class);

    public static final String RECEIVED_MESSAGE = "Received Message: {}";

    @KafkaListener(topics = "payments", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenPayments(@Payload String message) {
            logger.info(RECEIVED_MESSAGE, message);
            paymentService.savePaymentFromKafka(message);
            throw new ProcessingException("Testing DLQ.");
    }
}
