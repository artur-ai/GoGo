package com.maiboroda.GoGo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarRequestDto {

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Engine is required")
    private String engine;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @NotNull(message = "Daily price is required")
    private BigDecimal pricePerDay;

    private List<Long> tagIds;
}