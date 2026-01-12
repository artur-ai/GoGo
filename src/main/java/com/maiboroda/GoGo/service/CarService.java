package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CarService {
    List<CarResponseDto> getAllCars();
    List<CarResponseDto> getRandomCars();
    CarResponseDto addCar(CarRequestDto carRequestDto);

    CarResponseDto updateCarById(CarRequestDto carRequestDto, long id);

    List<CarResponseDto> findCarByCountry(String countryName);
}
