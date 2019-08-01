package com.example.xml.service.impl;

import com.example.xml.domain.dto.*;
import com.example.xml.domain.entities.User;
import com.example.xml.repository.UserRepository;
import com.example.xml.service.UserService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers(UserDtos dtos) {
        List<UserDto> userDtos = dtos.getUserDtos();
        for (UserDto userDto : userDtos) {
            if (!validatorUtil.isValid(userDto)) {
                validatorUtil.violations(userDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            User user = this.modelMapper.map(userDto, User.class);
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public UserSoldProductsDtos getUsersSoldProducts() {
        List<User> users = this.userRepository.getAllBySellContainsProductBuyer();
        List<UserSoldProductsDto> usersDtos = users.stream()
                .map(u -> {
                    UserSoldProductsDto user = this.modelMapper.map(u, UserSoldProductsDto.class);
                    SoldProductsDto[] soldProductsDtos = Arrays.stream(this.modelMapper.map(u.getProductsSold(), SoldProductsDto[].class))
                            .filter(dto -> dto.getBuyerFirstName() != null && dto.getBuyerLastName() != null)
                            .toArray(SoldProductsDto[]::new);
                    user.setSoldProductsDtoSet(Arrays.stream(soldProductsDtos).collect(Collectors.toSet()));
                    return user;
                })
                .filter(userSoldProductsDto -> userSoldProductsDto.getSoldProductsDtoSet().size() != 0)
                .collect(Collectors.toList());
        UserSoldProductsDtos userSoldProductsDtos = new UserSoldProductsDtos();
        userSoldProductsDtos.setDtos(usersDtos);
        return userSoldProductsDtos;
    }

    @Override
    public UsersAndProductsDtos getUsersAndProducts() {
        List<User> users = this.userRepository.getUsersAndProducts();
        List<UsersAndProductsDto> userAndSoldProductsDtos = users.stream()
                .map(u -> {
                    UsersAndProductsDto user = this.modelMapper.map(u, UsersAndProductsDto.class);
                    UserAndSoldProductsDto[] soldProductsDtos = this.modelMapper.map(u.getProductsSold(), UserAndSoldProductsDto[].class);
                    user.setSoldProductsDtos(Arrays.stream(soldProductsDtos).collect(Collectors.toSet()));
                    return user;
                }).collect(Collectors.toList());
        UsersAndProductsDtos usersAndProductsDtos = new UsersAndProductsDtos();
        usersAndProductsDtos.setDtos(userAndSoldProductsDtos);
        return usersAndProductsDtos;
    }
}
