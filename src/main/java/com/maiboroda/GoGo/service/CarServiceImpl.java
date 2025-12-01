package com.maiboroda.GoGo.service;


import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = Logger.getLogger(CarServiceImpl.class.getName());
    private final CarRepository carRepository;
    @Value("${gogo.settings.random-number}")
    int randomNumber;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.log(Level.INFO, "Successfully add all cars from repositiry", cars.size());
        return cars;
    }

    @Override
    public List<Car> getRandomCars() {
        if (randomNumber < 0) {
            throw new IllegalArgumentException("Invalid Number, it must be positive");
        }
        List<Car> cars = carRepository.getRandomCars(randomNumber);
        if (randomNumber > cars.size()) {
            throw new IllegalArgumentException("Invalid Number, it must be from 1 to " + cars.size());
        }
        logger.log(Level.INFO, "Successfully add" + cars.size() + "random cars");
        return cars;
    }

    @Override
    public CarResponseDto addCar(CarRequestDto carRequestDto) {
        Car car = convertToEntity(carRequestDto);
        Car savedCar = carRepository.save(car);
        logger.log(Level.INFO, "Successfully add car to db: " + savedCar.getBrand() + " " + savedCar.getModel() + " " + savedCar.getId());

        return convertToResponseDto(savedCar);
    }

    private Car convertToEntity(CarRequestDto carRequestDto) {
        Car car = new Car();
        car.setBrand(carRequestDto.getBrand());
        car.setModel(carRequestDto.getModel());
        car.setYear(carRequestDto.getYear());
        car.setFuelType(carRequestDto.getFuelType());
        car.setEngine(carRequestDto.getEngine());
        car.setPricePerMinute(carRequestDto.getPricePerMinute());
        car.setPricePerDay(carRequestDto.getPricePerDay());
        car.setInsurancePrice(carRequestDto.getInsurancePrice());
        car.setImageUrl(carRequestDto.getImageUrl());
        return car;
    }

    private CarResponseDto convertToResponseDto(Car car) {
        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(car.getId());
        carResponseDto.setBrand(car.getBrand());
        carResponseDto.setModel(car.getModel());
        carResponseDto.setYear(car.getYear());
        carResponseDto.setFuelType(car.getFuelType());
        carResponseDto.setEngine(car.getEngine());
        carResponseDto.setPricePerMinute(car.getPricePerMinute());
        carResponseDto.setPricePerDay(car.getPricePerDay());
        carResponseDto.setInsurancePrice(car.getInsurancePrice());
        carResponseDto.setImageUrl(car.getImageUrl());
        car.setCreatedAt(car.getCreatedAt());
        return carResponseDto;
    }
}