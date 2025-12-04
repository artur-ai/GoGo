package com.maiboroda.GoGo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarRequestDto {

    @NotBlank(message = "Brand can not be empty")
    @Size(max = 50, message = "Brand must be less than 50 characters")
    private String brand;

    @NotBlank(message = "Model can not be empty")
    @Size(max = 50, message = "Model must be less than 50 characters")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1930, message = "Year must be after 1930")
    @Max(value = 2026, message = "Year must be before 2026")
    private Integer year;

    @NotBlank(message = "Fuel type cannot be empty")
    @Size(max = 50, message = "Fuel type must be less than 50 characters")
    private String fuelType;

    @NotBlank(message = "Engine cannot be empty")
    @Size(max = 50, message = "Engine must be less than 50 characters")
    private String engine;

    @NotNull(message = "Price per minute is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per minute must be positive")
    private BigDecimal pricePerMinute;

    @NotNull(message = "Price per day is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be positive")
    private BigDecimal pricePerDay;

    @DecimalMin(value = "0.0", message = "Insurance price cannot be negative")
    private BigDecimal insurancePrice;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    private String imageUrl;
}
