package com.maiboroda.GoGo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(nullable = false, length = 50)
    private String fuelType;

    @Column(nullable = false, length = 50)
    private String engine;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double pricePerMinute;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double pricePerDay;

    @Column(precision = 10, scale = 2)
    private BigDecimal insurancePrice = BigDecimal.ZERO;

    @Column(length = 255)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Car() {
    }

    public Car(Long id, String brand, String model, Integer year, String fuelType,
               String engine, Double pricePerMinute, Double pricePerDay,
               BigDecimal insurancePrice, String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.engine = engine;
        this.pricePerMinute = pricePerMinute;
        this.pricePerDay = pricePerDay;
        this.insurancePrice = insurancePrice;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getEngine() {
        return engine;
    }

    public Double getPricePerMinute() {
        return pricePerMinute;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setPricePerMinute(Double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
