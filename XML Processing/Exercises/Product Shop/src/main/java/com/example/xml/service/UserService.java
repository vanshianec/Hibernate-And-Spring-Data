package com.example.xml.service;

import com.example.xml.domain.dto.UserDtos;
import com.example.xml.domain.dto.UserSoldProductsDtos;
import com.example.xml.domain.dto.UsersAndProductsDto;
import com.example.xml.domain.dto.UsersAndProductsDtos;

import java.util.List;

public interface UserService {
    void seedUsers(UserDtos userDtos);

    UserSoldProductsDtos getUsersSoldProducts();

    UsersAndProductsDtos getUsersAndProducts();
}
