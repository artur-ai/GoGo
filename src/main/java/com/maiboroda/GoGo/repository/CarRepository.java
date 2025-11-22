package com.maiboroda.GoGo.repository;


import com.maiboroda.GoGo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarByTagId(
            @Param("tagId") List<Long> tagId,
            @Param("tagCount") Long tagCount
    );
}
