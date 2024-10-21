package org.severinu.demoapi.api.controller;

import jakarta.websocket.server.PathParam;
import org.severinu.demoapi.interfacesfiddle.EmailNotification;
import org.severinu.demoapi.interfacesfiddle.INotificationSender;
import org.severinu.demoapi.interfacesfiddle.SmsNotification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final INotificationSender emailSender;
    private final INotificationSender smsSender;

    public MessageController(@EmailNotification INotificationSender emailSender,
                             @SmsNotification INotificationSender smsSender) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
    }

    @GetMapping
    public ResponseEntity<String> getAllMessages(@PathParam("message") String message) {
        emailSender.send(message);
        smsSender.send(message);

        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }
}
