package com.maiboroda.GoGo.kafka;

import com.maiboroda.GoGo.dto.CarNotificationDto;
import com.maiboroda.GoGo.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarNotificationConsumer {
    private static final String TOPIC = "car-notification";
    private static final String GROUP_ID = "gogo-group";

    private final EmailNotificationService emailNotificationService;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consumerCarNotification(CarNotificationDto notification) {
        log.info("📢 NEW CAR ADDED! Brand: {}, Model: {}, Year: {}",
                notification.brand(),
                notification.model(),
                notification.year());
        log.info("   Price per day: {}, Admin: {}",
                notification.pricePerDay(),
                notification.adminEmail());
        log.info("   Created at: {}", notification.createdAt());

        emailNotificationService.sendCarNotification(notification);
    }
}
