package org.severinu.demoapi.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class SqsService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue.url}")
    private String sqsQueueUrl;

    public SqsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedRate = 5000) // Poll every 5 seconds
    public void pollMessages() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .maxNumberOfMessages(5)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {
            System.out.println("Received message: " + message.body());
            // Process message...
        }
    }
}
