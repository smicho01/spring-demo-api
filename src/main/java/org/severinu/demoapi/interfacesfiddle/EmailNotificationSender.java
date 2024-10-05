package org.severinu.demoapi.interfacesfiddle;

import org.springframework.stereotype.Service;

@Service
@EmailNotification
public class EmailNotificationSender implements INotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending Email message: " + message);
    }
}
