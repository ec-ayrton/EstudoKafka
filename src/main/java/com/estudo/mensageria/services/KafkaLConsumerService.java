package com.estudo.mensageria.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaLConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLConsumerService.class);

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public  void listen(String message){
        logger.info("Received Message: {}",message );
    }

    @KafkaListener(topicPattern = "pedido-[0-9]+", groupId = "my-group")
    public void listenWithPattern(String message){
        logger.info("Received Message: {}",message );
    }
}
