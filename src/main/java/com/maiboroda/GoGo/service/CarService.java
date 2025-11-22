package com.maiboroda.GoGo.service;

import com.maiboroda.GoGo.entity.Car;
import com.maiboroda.GoGo.repository.CarRepository;
import com.maiboroda.GoGo.repository.TagRepository;
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
    private final TagRepository tagRepository;

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.log(Level.INFO, "Successfully add all cars from repositiry", cars.size());
        return cars;
    }

    public List<Car> findCarByTags(List<Long> tagId) {
        if (tagId == null || tagId.isEmpty()) {
            logger.warning("Tag list is empty, returning all cars");
            return getAllCars();
        }

        long tagCount = tagId.size();

        List<Car> filteredCars = carRepository.findCarByTagId(tagId, tagCount);
        logger.log(Level.INFO, "Successfully filtered" + filteredCars.size() + "cars");
        return filteredCars;
    }
}
