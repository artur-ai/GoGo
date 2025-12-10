package com.maiboroda.GoGo.service;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.AbstractIntegrationTest;
import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@DBRider
@DataSet("datasets/cars.yml")
public class CarServiceITest extends AbstractIntegrationTest {

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private CarRepository carRepository;

    private CarRequestDto createValidCarRequestDto() {
        return new CarRequestDto(
                "Dodge", "Dart", 2014, "Petrol", "2.4L",
                new BigDecimal("15.50"), new BigDecimal("1200.00"), new BigDecimal("10.00"),
                "https://test.com/dodge_dart.png"
        );
    }

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

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true, cleanAfter = true)
    void testAddCarReturnCorrectRespondeDto() {
        CarRequestDto requestDto = createValidCarRequestDto();
        CarResponseDto responseDto = carService.addCar(requestDto);

        assertNotNull(responseDto, "Response DTO should not be null");
        assertNotNull(responseDto.getId(), "Generated ID should not be null");
        assertEquals(requestDto.getBrand(), responseDto.getBrand());
        assertEquals(requestDto.getModel(), responseDto.getModel());
    }

    @Test
    @DataSet(value = "datasets/cars.yml", cleanBefore = true, cleanAfter = true)
    void testAddCarReturnCorrectCount() {
        long initialCount = carRepository.count();

        CarRequestDto requestDto = createValidCarRequestDto();
        CarResponseDto responseDto = carService.addCar(requestDto);

        long newCount = carRepository.count();
        assertEquals(initialCount + 1, newCount, "Car count in DB should increase by 1");

        Optional<Car> savedCarOptional = carRepository.findById(responseDto.getId());

        assertThat(savedCarOptional).isPresent();
        Car savedCar = savedCarOptional.get();

        assertEquals(requestDto.getEngine(), savedCar.getEngine());
        assertEquals(requestDto.getFuelType(), savedCar.getFuelType());
        assertEquals(requestDto.getYear(), savedCar.getYear());
    }

    @Test
    void testFindCarByCountry_Ukraine_ReturnsCorrectCount() {
        List<CarResponseDto> cars = carService.findCarByCountry("Ukraine");

        assertNotNull(cars);
        assertEquals(6, cars.size());
    }

    @Test
    void testFindCarByCountry_Poland_ReturnsCorrectCount() {
        List<CarResponseDto> cars = carService.findCarByCountry("Poland");

        assertNotNull(cars);
        assertEquals(7, cars.size());
    }

    @Test
    void testFindCarByCountry_Germany_ReturnsCorrectCount() {
        List<CarResponseDto> cars = carService.findCarByCountry("Germany");

        assertNotNull(cars);
        assertEquals(7, cars.size());
    }

    @Test
    void testFindCarByCountry_Italy_ReturnsCorrectBrands() {
        List<CarResponseDto> cars = carService.findCarByCountry("Italy");

        assertEquals(6, cars.size());

        Set<String> brands = cars.stream()
                .map(CarResponseDto::getBrand)
                .collect(Collectors.toSet());

        assertThat(brands).containsExactlyInAnyOrder(
                "Nissan", "Skoda", "Audi", "BMW", "Toyota", "Hyundai"
        );
    }

    @Test
    void testFindCarByCountry_Spain_ReturnsCorrectCars() {
        List<CarResponseDto> cars = carService.findCarByCountry("Spain");

        assertEquals(4, cars.size());

        List<String> models = cars.stream()
                .map(CarResponseDto::getModel)
                .sorted()
                .toList();

        assertThat(models).containsExactly("Camry", "Fiesta", "H-1", "Rapid");
    }

    @Test
    void testFindCarByCountry_NonExistentCountry_ThrowsException() {
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> carService.findCarByCountry("Australia")
        );

        assertThat(exception.getMessage())
                .contains("No cars found for country");
    }

    @Test
    void testFindCarByCountry_ReturnsCarResponseDtoWithAllFields() {
        List<CarResponseDto> cars = carService.findCarByCountry("Ukraine");

        CarResponseDto firstCar = cars.get(0);
        assertNotNull(firstCar.getId());
        assertNotNull(firstCar.getBrand());
        assertNotNull(firstCar.getModel());
        assertNotNull(firstCar.getYear());
        assertNotNull(firstCar.getFuelType());
        assertNotNull(firstCar.getEngine());
        assertNotNull(firstCar.getPricePerMinute());
        assertNotNull(firstCar.getPricePerDay());
        assertNotNull(firstCar.getInsurancePrice());
        assertNotNull(firstCar.getImageUrl());
        assertNotNull(firstCar.getCreatedAt());
    }
}
