package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.dto.PagedResponse;
import com.maiboroda.GoGo.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
@Slf4j
public class CarController {
    private final CarService carService;

    @GetMapping
    public PagedResponse<CarResponseDto> getAllCars(Pageable pageable) {
        log.info("Rest request to get all cars with pageable: {}", pageable);
        return carService.getAllCars(pageable);
    }

    @GetMapping("/random")
    public ResponseEntity<List<CarResponseDto>> getRandomCars() {
        try {
            List<CarResponseDto> cars = carService.getRandomCars();
            log.info("Retrieved {} random cars", cars.size());
            return ResponseEntity.status(HttpStatus.OK).body(cars);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @PostMapping()
    public ResponseEntity<CarResponseDto> addCar(@Valid @RequestBody CarRequestDto carRequestDto) {
        log.info("Creating new car: {} {}", carRequestDto.getBrand(), carRequestDto.getModel());
        CarResponseDto responseDto = carService.addCar(carRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateCar(@Valid @RequestBody CarRequestDto carRequestDto, @PathVariable Long id) {
        log.info("Updating car with id: {}", id);
        CarResponseDto responseDto = carService.updateCarById(carRequestDto, id);
        return ResponseEntity.ok(responseDto);
    }
}

