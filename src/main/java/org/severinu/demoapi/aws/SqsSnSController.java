package org.severinu.demoapi.aws;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snssqs")
public class SqsSnSController {

    private final SnsService snsService;

    public SqsSnSController(SnsService snsService) {
        this.snsService = snsService;
    }

    @PostMapping
    public void publishMessage() {
        snsService.publishMessage("Ala ma kota", "grupa1");
    }
}
