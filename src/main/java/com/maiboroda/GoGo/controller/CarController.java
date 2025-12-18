package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
@Slf4j
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> gelAllCars() {
        List<CarResponseDto> cars = carService.getAllCars();
        log.info("Retrieved {} cars", cars.size());
        return ResponseEntity.ok(cars);
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