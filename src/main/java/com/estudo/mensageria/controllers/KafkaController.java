package com.estudo.mensageria.controllers;

import com.estudo.mensageria.exceptions.ProcessingException;
import com.estudo.mensageria.models.Order;
import com.estudo.mensageria.models.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/send/payment")
    public void sendPayment(@RequestBody Payment payment){
        try {
            String jsonPayment = mapper.writeValueAsString(payment);
            kafkaTemplate.send("payments", jsonPayment);
        } catch (JsonProcessingException e) {
            throw new ProcessingException("Error processing payment.");
        }
    }

    @PostMapping("/send/order")
    public void sendOrder(@RequestBody Order order){
        try {
            String jsonOrder = mapper.writeValueAsString(order);
            kafkaTemplate.send("orders", jsonOrder);
        } catch (JsonProcessingException e) {
            throw new ProcessingException("Error processing order.");
        }
    }

}
