package com.example.demo.service;

import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.dto.UserSoldProductsDto;
import com.example.demo.domain.dto.UsersAndProductsDto;

import java.util.List;

public interface UserService {
    void seedUsers(UserDto[] userDtos);

    List<UserSoldProductsDto> getUsersSoldProducts();

    List<UsersAndProductsDto> getUsersAndProducts();
}
