package edu.prathap.reminder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaProducerService {

//    @Autowired
    private final KafkaConfig kafkaConfig;

    @Autowired
    public KafkaProducerService(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }
    public Boolean sendMessage(String topic, String message) {
        KafkaTemplate<String, Object> kafkaTemplate = kafkaConfig.kafkaTemplate();
        CompletableFuture<SendResult<String, Object>> future=null;
        try {
            future = kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if(future.isDone())
            return true;
        /*future.whenComplete((result, ex) -> {
            if (ex != null) {
                // Handle the exception, which could be a TimeoutException or other KafkaException
                System.err.println("Failed to send message: " + ex.getMessage());
                // Implement retry logic or dead-letter queue handling
            } else {
                System.out.println("Message sent successfully: " + result.getRecordMetadata().topic());
            }
        });*/
        return true;
    }

}
