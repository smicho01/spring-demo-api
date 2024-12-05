package org.severinu.demoapi.api.controller;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.severinu.demoapi.interfacesfiddle.EmailNotification;
import org.severinu.demoapi.interfacesfiddle.INotificationSender;
import org.severinu.demoapi.interfacesfiddle.SmsNotification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.severinu.demoapi.api.constants.DemoApiConstants.SCHEMA_HEADER;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final INotificationSender emailSender;
    private final INotificationSender smsSender;

    private final IHeadersService headersService;

    public MessageController(@EmailNotification INotificationSender emailSender,
                             @SmsNotification INotificationSender smsSender, IHeadersService headersService) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
        this.headersService = headersService;
    }

    @GetMapping
    public ResponseEntity<String> sendAllMessages(@PathParam("message") String message) {
        log.info("API /messages/sendAllMessages");
        emailSender.send(message);
        smsSender.send(message);

        log.info("Schema header sent with this request is: {}", headersService.get(SCHEMA_HEADER));

        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }
}
