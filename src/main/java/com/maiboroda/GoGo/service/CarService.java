package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class CarService {
    private static final Logger logger = Logger.getLogger(CarService.class.getName());
    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.log(Level.INFO, "Successfully add all cars from repositiry", cars.size());
        return cars;
    }
}
