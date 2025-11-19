package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.dto.CarRequestDto;
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
    public final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> gelAllCars(
            @RequestParam(required = false) List<Long> tagId) {
        List<Car> cars;

        if (tagId != null && !tagId.isEmpty()) {
            cars = carService.findCarByTags(tagId);
        } else {
            cars = carService.getAllCars();
        }
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody @Valid CarRequestDto carDto) {
        Car createdCar = carService.createCar(carDto);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }
}
