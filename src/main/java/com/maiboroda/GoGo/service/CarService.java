package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    List<Car> getRandomCars();

    CarResponseDto addCar(CarRequestDto carRequestDto);

    List<CarResponseDto> findCarByCountry(String countryName);
}
