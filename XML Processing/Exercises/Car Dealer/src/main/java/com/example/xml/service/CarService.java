package com.example.xml.service;

import com.example.xml.domain.dto.CarDtos;
import com.example.xml.domain.dto.queryDto.CarAndPartsDtos;
import com.example.xml.domain.dto.queryDto.ToyotaCarDtos;

public interface CarService {
    void seedCars(CarDtos carDtos);

    ToyotaCarDtos getToyotaCars();

    CarAndPartsDtos getCarsAndParts();
}
