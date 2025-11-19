package com.maiboroda.GoGo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Car.findCarByTagId",
        query = "SELECT c FROM Car c JOIN c.tags t WHERE t.id IN :tagId GROUP BY c.id HAVING COUNT(t.id) = :tagCount"
)
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

    @ManyToMany
    @JoinTable(
            name = "car-tags",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

}
