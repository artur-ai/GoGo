package com.maiboroda.GoGo.service;


import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.mapper.CarMapper;
import com.maiboroda.GoGo.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Value("${gogo.settings.random-number-car}")
    private int randomNumberCar;

    @Override
    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        log.info("Successfully add {} random car", cars.size());
        return carMapper.toResponseDtoList(cars);
    }

    @Override
    public List<CarResponseDto> getRandomCars() {
        if (randomNumberCar < 0) {
            throw new IllegalArgumentException("Invalid Number, it must be positive");
        }
        List<Car> cars = carRepository.getRandomCars(randomNumberCar);
        if (randomNumberCar > cars.size()) {
            throw new IllegalArgumentException("Invalid Number, it must be from 1 to " + cars.size());
        }
        log.info("Successfully add {} random cars", cars.size());
        return carMapper.toResponseDtoList(cars);
    }

    @Override
    public CarResponseDto addCar(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        Car savedCar = carRepository.save(car);
        log.info("Successfully add car to db: {} {} {}", savedCar.getBrand(), savedCar.getModel(), savedCar.getId());

        return carMapper.toResponseDto(savedCar);
    }

    @Transactional
    @Override
    public CarResponseDto updateCarById(CarRequestDto carRequestDto, long id) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found by id: " + id));
        carMapper.updateCarFromDto(carRequestDto, existingCar);
        Car updatedCar = carRepository.save(existingCar);

        return carMapper.toResponseDto(updatedCar);
    }
}