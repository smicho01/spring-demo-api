package org.severinu.demoapi.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.UUID;

@Service
public class SnsService {

    private final SnsClient snsClient;

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void publishMessage(String message, String messageGroupId) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(snsTopicArn)
                .messageGroupId(messageGroupId) // Mandatory for FIFO topics
                .message(message)
                .messageDeduplicationId(UUID.randomUUID().toString()) // Ensure uniqueness
                .build();
        snsClient.publish(request);
    }
}