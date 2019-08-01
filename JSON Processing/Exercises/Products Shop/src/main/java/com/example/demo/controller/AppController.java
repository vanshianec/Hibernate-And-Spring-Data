package com.example.demo.controller;

import com.example.demo.domain.dto.*;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.util.FileUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final static String USER_JSON_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Products Shop\\src\\main\\resources\\json-data\\users.json";
    private final static String CATEGORY_JSON_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Products Shop\\src\\main\\resources\\json-data\\categories.json";
    private final static String PRODUCT_JSON_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Products Shop\\src\\main\\resources\\json-data\\products.json";
    private final Gson gson;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public AppController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedUsers();
        //seedCategories();
        //seedProducts();
        //printProductsInPriceRange();
        //printUsersSoldProducts();
        //printCategoriesByProducts();
        //printUsersAndProducts();
    }

    private void seedUsers() throws IOException {
        String content = this.fileUtil.fileContent(USER_JSON_FILE_PATH);
        UserDto[] userDtos = this.gson.fromJson(content, UserDto[].class);
        this.userService.seedUsers(userDtos);
    }

    private void seedCategories() throws IOException {
        String content = this.fileUtil.fileContent(CATEGORY_JSON_FILE_PATH);
        CategoryDto[] categoryDtos = this.gson.fromJson(content, CategoryDto[].class);
        this.categoryService.seedCategories(categoryDtos);
    }

    private void seedProducts() throws IOException {
        String content = this.fileUtil.fileContent(PRODUCT_JSON_FILE_PATH);
        ProductDto[] categoryDtos = this.gson.fromJson(content, ProductDto[].class);
        this.productService.seedProducts(categoryDtos);
    }

    private void printProductsInPriceRange() {
        //products in price range between 500 and 1000
        BigDecimal lowerBound = new BigDecimal("500");
        BigDecimal higherBound = new BigDecimal("1000");
        List<ProductSellerDto> productDtos = this.productService.getProductsInRange(lowerBound, higherBound);
        System.out.println(this.gson.toJson(productDtos));
    }

    private void printUsersSoldProducts() {
        List<UserSoldProductsDto> userSoldProductsDtos = this.userService.getUsersSoldProducts();
        System.out.println(this.gson.toJson(userSoldProductsDtos));
    }

    private void printCategoriesByProducts() {
        List<CategoriesByProductsDto> categoriesByProductsDtos = this.categoryService.getCategoriesByProducts();
        System.out.println(this.gson.toJson(categoriesByProductsDtos));
    }

    private void printUsersAndProducts() {
        List<UsersAndProductsDto> usersAndProductsDtos = this.userService.getUsersAndProducts();
        System.out.println(this.gson.toJson(usersAndProductsDtos));
    }
}
