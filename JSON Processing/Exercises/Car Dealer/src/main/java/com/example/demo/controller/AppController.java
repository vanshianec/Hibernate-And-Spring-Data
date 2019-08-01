package com.example.demo.controller;

import com.example.demo.domain.dto.CarDto;
import com.example.demo.domain.dto.CustomerDto;
import com.example.demo.domain.dto.PartDto;
import com.example.demo.domain.dto.SupplierDto;
import com.example.demo.domain.dto.queryDto.*;
import com.example.demo.service.*;
import com.example.demo.util.FileUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {
    private static final String SUPPLIER_JSON_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Car Dealer\\src\\main\\resources\\json-data\\suppliers.json";
    private static final String PART_JSON_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Car Dealer\\src\\main\\resources\\json-data\\parts.json";
    private static final String CAR_JSON_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Car Dealer\\src\\main\\resources\\json-data\\cars.json";
    private static final String CUSTOMER_JSON_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\JSON Processing\\Exercises\\Car Dealer\\src\\main\\resources\\json-data\\customers.json";
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public AppController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }


    @Override
    public void run(String... args) throws Exception {
//        seedSuppliers();
//        seedParts();
//        seedCars();
//        seedCustomers();
//        seedSales();
        //printCustomersByBirthDate();
        //printToyotaCars();
        //printLocalSuppliers();
        //printCarsAndParts();
        //printCustomersTotalSales();
        //printSalesWithDiscounts();
    }

    private void seedSuppliers() throws IOException {
        String content = this.fileUtil.fileContent(SUPPLIER_JSON_PATH);
        SupplierDto[] supplierDtos = this.gson.fromJson(content, SupplierDto[].class);
        this.supplierService.seedSuppliers(supplierDtos);
    }

    private void seedParts() throws IOException {
        String content = this.fileUtil.fileContent(PART_JSON_PATH);
        PartDto[] partDtos = this.gson.fromJson(content, PartDto[].class);
        this.partService.seedParts(partDtos);
    }

    private void seedCars() throws IOException {
        String content = this.fileUtil.fileContent(CAR_JSON_PATH);
        CarDto[] carDtos = this.gson.fromJson(content, CarDto[].class);
        this.carService.seedCars(carDtos);
    }

    private void seedCustomers() throws IOException {
        String content = this.fileUtil.fileContent(CUSTOMER_JSON_PATH);
        CustomerDto[] customerDtos = this.gson.fromJson(content, CustomerDto[].class);
        this.customerService.seedCustomers(customerDtos);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void printCustomersByBirthDate() {
        OrderedCustomersDto[] customersDtos = this.customerService.getCustomersByBirthDate();
        System.out.println(this.gson.toJson(customersDtos));
    }

    private void printToyotaCars() {
        ToyotaCarDto[] toyotaCarDtos = this.carService.getToyotaCars();
        System.out.println(this.gson.toJson(toyotaCarDtos));
    }

    private void printLocalSuppliers() {
        LocalSupplierDto[] localSupplierDtos = this.supplierService.getLocalSuppliers();
        System.out.println(this.gson.toJson(localSupplierDtos));
    }

    private void printCarsAndParts() {
        CarAndPartsDto[] carAndPartsDtos = this.carService.getCarsAndParts();
        System.out.println(this.gson.toJson(carAndPartsDtos));
    }

    private void printCustomersTotalSales() {
        CustomerTotalSalesDto[] customerTotalSalesDtos = this.customerService.getCustomersTotalSales();
        System.out.println(this.gson.toJson(customerTotalSalesDtos));
    }

    private void printSalesWithDiscounts() {
        SaleDiscountsDto[] saleDiscountsDtos = this.saleService.getSalesWithDiscount();
        System.out.println(this.gson.toJson(saleDiscountsDtos));
    }
}
