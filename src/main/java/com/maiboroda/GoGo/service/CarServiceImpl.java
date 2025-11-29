package com.maiboroda.GoGo.service;


import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = Logger.getLogger(CarServiceImpl.class.getName());
    private final CarRepository carRepository;
    @Value("${gogo.settings.random-number}")
    int randomNumber;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.log(Level.INFO, "Successfully add all cars from repositiry", cars.size());
        return cars;
    }

    @Override
    public List<Car> getRandomCars() {
        List<Car> cars = carRepository.getRandomCars(randomNumber);
        if (randomNumber > cars.size() || randomNumber < 0) {
            throw new IllegalArgumentException("Invalid Number, it must be from 1 to " + cars.size());
        }
        logger.log(Level.INFO, "Successfully add" + cars.size() + "random cars");
        return cars;
    }
}