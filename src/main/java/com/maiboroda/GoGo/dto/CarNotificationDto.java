package com.maiboroda.GoGo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarNotificationDto(
        Long carId,
        String brand,
        String model,
        Integer year,
        String fuelType,
        String engine,
        BigDecimal pricePerDay,
        BigDecimal pricePerMinute,
        String imageUrl,
        LocalDateTime createdAt,
        String adminEmail
) {
}
