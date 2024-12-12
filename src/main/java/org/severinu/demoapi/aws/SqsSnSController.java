package org.severinu.demoapi.aws;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snssqs")
public class SqsSnSController {

    private final SnsService snsService;

    private final SqsService sqsService;

    public SqsSnSController(SnsService snsService, SqsService sqsService) {
        this.snsService = snsService;
        this.sqsService = sqsService;
    }

    @PostMapping
    public void publishMessage() {
        snsService.publishMessage("Ala ma kota", "grupa1");
        //sqsService.pollMessages();
    }
}
