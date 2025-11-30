package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getRandomCars();
}
