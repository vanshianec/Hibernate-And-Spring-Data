package com.example.demo.service.impl;

import com.example.demo.domain.dto.CarDto;
import com.example.demo.domain.dto.queryDto.CarAndPartsDto;
import com.example.demo.domain.dto.queryDto.PartForCarDto;
import com.example.demo.domain.dto.queryDto.ToyotaCarDto;
import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.Part;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.PartRepository;
import com.example.demo.service.CarService;
import com.example.demo.util.ValidatorUtil;
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
    public void seedCars(CarDto[] carDtos) {
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
    public ToyotaCarDto[] getToyotaCars() {
        List<Car> toyotaCars = this.carRepository.findByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        return this.modelMapper.map(toyotaCars, ToyotaCarDto[].class);
    }

    @Override
    public CarAndPartsDto[] getCarsAndParts() {
        return this.carRepository.findAll().stream()
                .map(c -> {
                    CarAndPartsDto carDto = this.modelMapper.map(c, CarAndPartsDto.class);
                    Set<PartForCarDto> partsDto = Arrays.stream(this.modelMapper.map(c.getParts(), PartForCarDto[].class)).collect(Collectors.toSet());
                    carDto.setParts(partsDto);
                    return carDto;
                }).toArray(CarAndPartsDto[]::new);
    }
}
