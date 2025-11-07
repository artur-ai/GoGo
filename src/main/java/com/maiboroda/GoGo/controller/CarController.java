package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarController {
    public final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> gelAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }
}
