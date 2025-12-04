package com.maiboroda.GoGo.service;


import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.mapper.CarMapper;
import com.maiboroda.GoGo.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Value("${gogo.settings.random-number}")
    private int randomNumber;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        log.info("Successfully add {} random car", cars.size());
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
        log.info("Successfully add{}random cars", cars.size());
        return cars;
    }

    @Override
    public CarResponseDto addCar(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        Car savedCar = carRepository.save(car);
        log.info("Successfully add car to db: {} {} {}", savedCar.getBrand(), savedCar.getModel(), savedCar.getId());

        return carMapper.toResponseDto(savedCar);
    }
}