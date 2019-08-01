package com.example.xml.service.impl;

import com.example.xml.domain.dto.CarDto;
import com.example.xml.domain.dto.CarDtos;
import com.example.xml.domain.dto.queryDto.*;
import com.example.xml.domain.entity.Car;
import com.example.xml.domain.entity.Part;
import com.example.xml.repository.CarRepository;
import com.example.xml.repository.PartRepository;
import com.example.xml.service.CarService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PartRepository partRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.partRepository = partRepository;
    }

    @Override
    public void seedCars(CarDtos dtos) {
        List<CarDto> carDtos = dtos.getDtos();
        for (CarDto carDto : carDtos) {
            if (!validatorUtil.isValid(carDto)) {
                validatorUtil.printErrors(carDto);
                continue;
            }
            Car car = this.modelMapper.map(carDto, Car.class);
            car.setParts(randomParts());
            this.carRepository.saveAndFlush(car);
        }
    }


    private Set<Part> randomParts() {
        Random random = new Random();
        int size = random.nextInt(11) + 10;
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < size; i++) {
            parts.add(randomPart());
        }
        return parts;
    }

    private Part randomPart() {
        Random random = new Random();
        int id = random.nextInt((int) this.partRepository.count()) + 1;
        return this.partRepository.findById(id);
    }


    @Override
    public ToyotaCarDtos getToyotaCars() {
        List<Car> toyotaCars = this.carRepository.findByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        ToyotaCarDtos toyotaCarDtos = new ToyotaCarDtos();
        List<ToyotaCarDto> dtos = Arrays.stream(this.modelMapper.map(toyotaCars, ToyotaCarDto[].class)).collect(Collectors.toList());
        toyotaCarDtos.setDtos(dtos);
        return toyotaCarDtos;
    }

    @Override
    public CarAndPartsDtos getCarsAndParts() {
        CarAndPartsDtos carAndPartsDtos = new CarAndPartsDtos();
        List<CarAndPartsDto> dtos = this.carRepository.findAll().stream()
                .map(c -> {
                    CarAndPartsDto carDto = this.modelMapper.map(c, CarAndPartsDto.class);
                    Set<PartForCarDto> partsDto = Arrays.stream(this.modelMapper.map(c.getParts(), PartForCarDto[].class)).collect(Collectors.toSet());
                    carDto.setParts(partsDto);
                    return carDto;
                }).collect(Collectors.toList());
        carAndPartsDtos.setDtos(dtos);
        return carAndPartsDtos;
    }
}
