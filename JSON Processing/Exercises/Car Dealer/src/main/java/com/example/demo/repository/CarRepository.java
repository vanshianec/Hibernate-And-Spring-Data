package com.example.demo.repository;

import com.example.demo.domain.dto.queryDto.CarAndPartsDto;
import com.example.demo.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
