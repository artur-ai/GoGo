package com.maiboroda.GoGo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 50, name = "fuel_type")
    private String fuelType;

    @Column(nullable = false, length = 50)
    private String engine;

    @Column(nullable = false, name = "price_per_minute")
    private BigDecimal pricePerMinute;

    @Column(nullable = false, name = "price_per_day")
    private BigDecimal pricePerDay;

    @Column(nullable = false, name = "insurance_price")
    private BigDecimal insurancePrice = BigDecimal.ZERO;

    @Column(length = 255, name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToMany
    @JoinTable(
            name = "car_countries",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @JsonIgnoreProperties("cars")
    private List<Country> countries = new ArrayList<>();
}
