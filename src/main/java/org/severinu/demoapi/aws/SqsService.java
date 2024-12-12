package org.severinu.demoapi.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SqsService {

    private final SqsClient sqsClient;

    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue.url}")
    private String sqsQueueUrl;

    /**
     * This method will pull for new messages in SQS queue
     * Check README.md for instructions to set up SQS for SNS
     */
    @Scheduled(fixedRate = 2000) // Poll every 2 seconds
    public void pollMessages() throws JsonProcessingException {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .maxNumberOfMessages(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {
            //System.out.println("Received message: " + message.body());

            NotificationMessage notificationMessage = objectMapper.readValue(message.body(), NotificationMessage.class);
            log.info("SQS Message: {} ", notificationMessage.getMessage());

            // Deserialize the nested message
            MessageContent messageContent = objectMapper.readValue(notificationMessage.getMessage(), MessageContent.class);
            log.info("Correlation ID from SQS: {}", messageContent.getCorrelationId());
            log.info("Message: {}", messageContent.getMessage());

            // Delete the message to mark it as consumed
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(sqsQueueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        }
    }
}
