package com.estudo.mensageria.services;

import com.estudo.mensageria.models.Payment;
import com.estudo.mensageria.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void savePaymentFromKafka(String message){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Payment payment = mapper.readValue(message, Payment.class);
            paymentRepository.save(payment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
