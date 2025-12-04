package com.maiboroda.GoGo.repository;

import com.maiboroda.GoGo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "SELECT id, brand, model, year, fuel_type, engine, price_per_minute, price_per_day, insurance_price, image_url, created_at " +
            "FROM cars ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Car> getRandomCars(@Param("count") int count);
}