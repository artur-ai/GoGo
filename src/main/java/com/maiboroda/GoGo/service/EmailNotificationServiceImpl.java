package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.CarNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private final JavaMailSender mailSender;

    @Value("${app.notification.emails}")
    private String notificationsEmails;

    @Override
    public void sendCarNotification(CarNotificationDto notification) {
        try {
            String[] recipients = notificationsEmails.split(",");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipients);
            message.setFrom("noreply@gogo.com");
            message.setSubject("New car added to GoGo: " + notification.brand() + " " + notification.model());
            message.setText(buildEmailContent(notification));

            mailSender.send(message);
            log.info("✉️ Email notification sent to {} recipients", recipients.length);
        } catch (Exception exception) {
            log.error("Failed to send email notification", exception);
        }

    }

    private String buildEmailContent(CarNotificationDto notification) {
        return String.format(
                "🎉 New Car Available!\n\n" +
                        "Brand: %s\n" +
                        "Model: %s\n" +
                        "Year: %d\n" +
                        "Fuel Type: %s\n" +
                        "Engine: %s\n\n" +
                        "💰 Pricing:\n" +
                        "  Per Day: $%.2f\n" +
                        "  Per Minute: $%.2f\n" +
                        "📍 Added by: %s\n" +
                        "⏰ Created at: %s\n\n" +
                        "Check it out in the catalog!",
                notification.brand(),
                notification.model(),
                notification.year(),
                notification.fuelType(),
                notification.engine(),
                notification.pricePerDay(),
                notification.pricePerMinute(),
                notification.adminEmail(),
                notification.createdAt()
        );
    }
}
