package com.maiboroda.GoGo.repository;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void testFindAllCars() {
        List<Car> cars = carRepository.findAll();

        assertFalse(cars.isEmpty());
    }

    @Test
    void testFindCarsByBrand() {
        List<Car> cars = carRepository.findAll();
        Car skoda = cars.stream()
                .filter(car -> "Skoda".equals(car.getBrand()))
                .findFirst()
                .orElse(null);

        assertThat(skoda).isNotNull();
        assertThat(skoda.getModel()).isEqualTo("Fabia");
        assertThat(skoda.getYear()).isEqualTo(2013);
    }

    @Test
    void testGetCorrectImage() {
        List<Car> cars = carRepository.findAll();

        cars.forEach(car -> {
            assertThat(car.getImageUrl()).isNotNull();
            assertThat(car.getImageUrl()).startsWith("https://res.cloudinary.com/");
        });
    }
}