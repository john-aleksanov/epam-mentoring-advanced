package com.epam.messaging.producer;

import com.epam.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {

    private final AmqpTemplate template;

    @PostMapping("/message")
    public String sendMessage(@RequestBody Notification notification) {
        template.convertAndSend(notification.getCustomerId(), notification);
        return "Notification sent";
    }
}
