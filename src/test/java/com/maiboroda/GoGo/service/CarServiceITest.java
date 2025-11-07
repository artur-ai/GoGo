package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

public class CarServiceITest extends AbstractIntegrationTest {

    @Autowired
    private CarService carService;

    @Test
    void testGetAllCars() {
        List<Car> cars = carService.getAllCars();

        assertFalse(cars.isEmpty());
        assertEquals(12, cars.size());
    }

    @Test
    void testReturnCarsWithAllFields() {
        List<Car> cars = carService.getAllCars();
        Car firstCar = cars.get(0);

        assertEquals(1, firstCar.getId());
        assertEquals("Skoda", firstCar.getBrand());
        assertEquals("Fabia", firstCar.getModel());
        assertEquals(2013, firstCar.getYear());
        assertEquals("Petrol/Gas", firstCar.getFuelType());
        assertEquals("1.2L", firstCar.getEngine());
        assertEquals("https://res.cloudinary.com/de6b0q56z/image/upload/v1762162805/skoda-fabia_nvxaiq.png", firstCar.getImageUrl());
    }
}
