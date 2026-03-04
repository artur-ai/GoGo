package com.maiboroda.GoGo.kafka;

import com.maiboroda.GoGo.dto.CarNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarNotificationProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "car-notification";

    public void sendCarNotification(CarNotificationDto notification) {
        log.info("Sending car notification to Kafka: {} {}", notification.brand(), notification.model());
        kafkaTemplate.send(TOPIC, notification.carId().toString(), notification);
    }
}
