package com.example.xml.repository;

import com.example.xml.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
