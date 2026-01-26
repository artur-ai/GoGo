package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.dto.PagedResponse;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CarService {
    PagedResponse<CarResponseDto> getAllCars(Pageable pageable);
    List<CarResponseDto> getRandomCars();
    CarResponseDto addCar(CarRequestDto carRequestDto);

    CarResponseDto updateCarById(CarRequestDto carRequestDto, long id);
}
