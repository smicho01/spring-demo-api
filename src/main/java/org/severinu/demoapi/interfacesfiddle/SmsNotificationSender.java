package org.severinu.demoapi.interfacesfiddle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@SmsNotification
@Slf4j
public class SmsNotificationSender implements INotificationSender {
    @Override
    public void send(String message) {
        log.info("Sending SMS message: {}",  message);
    }
}
