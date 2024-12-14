package org.severinu.demoapi.api.snssqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.UUID;

@Service
public class SnsService {

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;


    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    public SnsService(SnsClient snsClient, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.objectMapper = objectMapper;
    }

    public void publishMessageToSNS(MessageContent message, String messageGroupId) throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(message);
        PublishRequest request = PublishRequest.builder()
                .topicArn(snsTopicArn)
                .messageGroupId(messageGroupId) // Mandatory for FIFO topics
                .message(messageJson)
                .messageDeduplicationId(UUID.randomUUID().toString()) // Ensure uniqueness
                .build();
        snsClient.publish(request);
    }
}