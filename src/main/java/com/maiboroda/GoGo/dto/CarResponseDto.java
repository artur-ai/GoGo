package com.maiboroda.GoGo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private String fuelType;
    private String engine;
    private BigDecimal pricePerMinute;
    private BigDecimal pricePerDay;
    private BigDecimal insurancePrice;
    private String imageUrl;
    private LocalDateTime createdAt;
    private List<String> countries;
}
