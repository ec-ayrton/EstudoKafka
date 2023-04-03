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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DeadLetterListener {

    @Autowired
    private DeadLetterRepository deadLetterRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);

    @KafkaListener(topics = "DeadQueue.DLQ", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenDeadLetter(@Payload ConsumerRecord<String, String> data) {
        logger.warn("Received message from dead letter queue. Persisting in database.");

        DeadLetter deadLetter = new DeadLetter();
        deadLetter.setTopic(data.topic());
        deadLetter.setPartition(data.partition());
        deadLetter.setDlqOffset(data.offset());
        deadLetter.setDlqKey(data.key());
        deadLetter.setDlqValue(data.value());
        deadLetter.setDlqDate(getHourDeadQueue());

        deadLetterRepository.save(deadLetter);
    }

    private String getHourDeadQueue(){
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return now.format(formatter);
    }
}
