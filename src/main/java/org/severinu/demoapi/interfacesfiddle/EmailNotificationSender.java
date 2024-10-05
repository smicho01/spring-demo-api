package org.severinu.demoapi.interfacesfiddle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@EmailNotification
@Slf4j
public class EmailNotificationSender implements INotificationSender {
    @Override
    public void send(String message) {
        log.info("Sending Email message: {}",  message);
    }
}
