package org.severinu.demoapi.api.snssqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snssqs")
@RequiredArgsConstructor
public class SqsSnSController {

    private final SnsService snsService;

    @PostMapping
    public ResponseEntity<String> publishMessage(@RequestBody MessageContent message) throws JsonProcessingException {
        String correlationId = MDC.get("correlationId");
        message.setCorrelationId(correlationId);
        snsService.publishMessageToSNS(message, "grupa1");

        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = objectMapper.writeValueAsString(message);
        String responseMessage = String.format("Message sent successfully\n%s", messageJson);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
