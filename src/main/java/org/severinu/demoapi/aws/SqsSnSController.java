package org.severinu.demoapi.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/snssqs")
@RequiredArgsConstructor
public class SqsSnSController {

    private final SnsService snsService;

    @PostMapping
    public void publishMessage(@RequestBody MessageContent message) throws JsonProcessingException {
        message.setCorrelationId(MDC.get("correlationId"));
        snsService.publishMessageToSNS(message, "grupa1");
    }
}
