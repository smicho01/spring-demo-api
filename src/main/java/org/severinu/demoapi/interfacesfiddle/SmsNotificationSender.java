package org.severinu.demoapi.interfacesfiddle;

import org.springframework.stereotype.Service;

@Service
@SmsNotification
public class SmsNotificationSender implements INotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS message: " + message);
    }
}
