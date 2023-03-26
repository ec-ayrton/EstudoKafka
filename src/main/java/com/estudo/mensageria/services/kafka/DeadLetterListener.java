package com.estudo.mensageria.services.kafka;

import com.estudo.mensageria.models.DeadLetter;
import com.estudo.mensageria.repositories.DeadLetterRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterListener {

    @Autowired
    private DeadLetterRepository deadLetterRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);

    @KafkaListener(topics = "orders.DLT", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenDeadLetter(@Payload ConsumerRecord<String, String> data) {
        logger.warn("Received message from dead letter queue. Persisting in database.");

        DeadLetter deadLetter = new DeadLetter();
        deadLetter.setTopic(data.topic());
        deadLetter.setPartition(data.partition());
        deadLetter.setDltOffset(data.offset());
        deadLetter.setDltKey(data.key());
        deadLetter.setDltValue(data.value());

        deadLetterRepository.save(deadLetter);
    }
}
