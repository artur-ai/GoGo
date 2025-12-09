package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.CarRequestDto;
import com.maiboroda.GoGo.dto.CarResponseDto;
import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> gelAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/random")
    public ResponseEntity<List<Car>> getRandomCars() {
        try {
            List<Car> cars = carService.getRandomCars();
            return ResponseEntity.ok(cars);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CarResponseDto> addCar(@Valid @RequestBody CarRequestDto carRequestDto) {
        CarResponseDto responseDto = carService.addCar(carRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateCar(@Valid @RequestBody CarRequestDto carRequestDto, @PathVariable long id) {
        CarResponseDto responseDto = carService.updateCarById(carRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}