package com.estudo.mensageria.services;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaLConsumerService {

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public  void listen(String message){
        System.out.println("Received Message: " + message);
    }
}
