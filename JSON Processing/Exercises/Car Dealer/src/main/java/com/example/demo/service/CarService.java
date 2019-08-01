package com.example.demo.service;

import com.example.demo.domain.dto.CarDto;
import com.example.demo.domain.dto.queryDto.CarAndPartsDto;
import com.example.demo.domain.dto.queryDto.ToyotaCarDto;

import java.util.List;

public interface CarService {
    void seedCars(CarDto[] carDtos);

    ToyotaCarDto[] getToyotaCars();

    CarAndPartsDto[] getCarsAndParts();
}
