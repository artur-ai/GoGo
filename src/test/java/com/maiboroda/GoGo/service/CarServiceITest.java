package com.maiboroda.GoGo.service;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DBRider
@DataSet("datasets/cars.yml")
public class CarServiceITest extends AbstractIntegrationTest {

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testGetAllCars_ReturnCorrectSize() {
        List<Car> cars = carService.getAllCars();

        assertFalse(cars.isEmpty());
        assertEquals(12, cars.size());
    }

    @Test
    void testReturnFirstCarWithAllFields() {
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

    @Test
    void testGetThreeRandomCar() {
        List<Car> cars = carService.getRandomCars();

        assertFalse(cars.isEmpty());
        assertEquals(3, cars.size());
    }

    @Test
    void testGetAllCars_ContainsExpectedBrands() {
        List<Car> cars = carService.getAllCars();
        Set<String> brands = cars.stream()
                .map(Car::getBrand)
                .collect(Collectors.toSet());

        assertThat(brands).containsExactlyInAnyOrder(
                "Skoda", "Ravon", "Volkswagen", "Ford",
                "Nissan", "Audi", "BMW", "Toyota", "Hyundai"
        );
    }

    @Test
    void testGetAllCars_ContainsExpectedFuelTypes() {
        List<Car> cars = carService.getAllCars();
        Set<String> fuelTypes = cars.stream()
                .map(Car::getFuelType)
                .collect(Collectors.toSet());

        assertThat(fuelTypes).containsExactlyInAnyOrder(
                "Petrol", "Diesel", "Electro", "Petrol/Gas"
        );
    }

    @Test
    void testServiceUsesRepository() {
        List<Car> serviceCars = carService.getAllCars();
        List<Car> repositoryCars = carRepository.findAll();

        assertEquals(repositoryCars.size(), serviceCars.size());
    }

    @Test
    void testGetRandomCars_UsesRepositoryMethod() {
        ReflectionTestUtils.setField(carService, "randomNumber", 3);

        List<Car> serviceCars = carService.getRandomCars();
        List<Car> repositoryCars = carRepository.getRandomCars(3);

        assertEquals(repositoryCars.size(), serviceCars.size());
    }


}
