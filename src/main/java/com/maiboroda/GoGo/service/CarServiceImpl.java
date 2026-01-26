package com.maiboroda.GoGo.service;


import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.dto.PagedResponse;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.mapper.CarMapper;
import com.maiboroda.GoGo.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CountryService countryService;

    @Value("${gogo.settings.random-number}")
    private int randomNumber;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CarResponseDto> getAllCars(Pageable pageable) {
        Page<Car> carPage = carRepository.findAll(pageable);
        List<CarResponseDto> cars = carMapper.toResponseDtoList(carPage.getContent());
        log.info("Retrieved {} cars for page {}", cars.size(), pageable.getPageNumber());
        return PagedResponse.of(carPage, cars);
    }

    @Override
    public List<CarResponseDto> getRandomCars() {
        if (randomNumber <= 0) {
            throw new IllegalArgumentException("Invalid Number, it must be positive");
        }
        List<Car> cars = carRepository.getRandomCars(randomNumber);
        if (randomNumber > cars.size()) {
            throw new IllegalArgumentException("Invalid Number, it must be from 1 to " + cars.size());
        }
        log.info("Successfully add {} random cars", cars.size());
        return carMapper.toResponseDtoList(cars);
    }

    @Override
    @Transactional
    public CarResponseDto addCar(CarRequestDto carRequestDto) {
        Car car = carMapper.toEntity(carRequestDto);
        Car savedCar = carRepository.save(car);
        log.info("Successfully add car to db: {} {} {}", savedCar.getBrand(), savedCar.getModel(), savedCar.getId());

        return carMapper.toResponseDto(savedCar);
    }

    @Override
    @Transactional
    public CarResponseDto updateCarById(CarRequestDto carRequestDto, long id) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found by id: " + id));
        carMapper.updateCarFromDto(carRequestDto, existingCar);
        Car updatedCar = carRepository.save(existingCar);

        return carMapper.toResponseDto(updatedCar);
    }


    @Override
    @Transactional
    public List<CarResponseDto> findCarByCountry(String countryName) {
        countryService.getCountryByName(countryName);
        List<Car> cars = carRepository.findByCountriesName(countryName);

        if (cars.isEmpty()) {
            throw new EntityNotFoundException("No cars found for country: " + countryName);
        }
        log.info("Found {} cars for country: {}", cars.size(), countryName);

        return cars.stream()
                .map(carMapper::toResponseDto)
                .toList();
    }
}