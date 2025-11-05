package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class CarServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CarService carService;

    @Test
    void testGetAllCars() {
        List<Car> cars = carService.getAllCars();

        assertThat(cars).isNotEmpty();
        assertThat(cars.size()).isEqualTo(12);
    }

    @Test
    void testReturnCarsWithAllFields() {
        List<Car> cars = carService.getAllCars();
        Car firstCar = cars.get(0);

        assertThat(firstCar.getId()).isNotNull();
        assertThat(firstCar.getBrand()).isNotNull();
        assertThat(firstCar.getModel()).isNotNull();
        assertThat(firstCar.getYear()).isGreaterThan(2000);
        assertThat(firstCar.getEngine()).isNotNull();
        assertThat(firstCar.getPricePerDay()).isNotNull();
        assertThat(firstCar.getImageUrl()).isNotNull();
    }
}
