package com.estudo.mensageria.services;

import com.estudo.mensageria.models.Order;
import com.estudo.mensageria.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrderFromKafka(String message){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Order order = mapper.readValue(message, Order.class);
            orderRepository.save(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
