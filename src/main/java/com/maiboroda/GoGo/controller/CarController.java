package com.maiboroda.GoGo.controller;

import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.service.CarService;
import lombok.AllArgsConstructor;
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
            @RequestParam(required = false) List<Long> tagIds) {
        List<Car> cars;

        if (tagIds != null && !tagIds.isEmpty()) {
            cars = carService.findCarByTags(tagIds);
        } else {
            cars = carService.getAllCars();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/random")
    public ResponseEntity<List<Car>> getThreeRandomCar(@RequestParam(defaultValue = "3") int count) {
        List<Car> cars = carService.getThreeRandomCars(count);
        return ResponseEntity.ok(cars);
    }
}
