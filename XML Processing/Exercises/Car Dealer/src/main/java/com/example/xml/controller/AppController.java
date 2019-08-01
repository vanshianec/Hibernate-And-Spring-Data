package com.example.xml.controller;

import com.example.xml.domain.dto.*;
import com.example.xml.domain.dto.queryDto.*;
import com.example.xml.service.*;
import com.example.xml.util.FileUtil;
import com.example.xml.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {
    private static final String SUPPLIER_XML_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Car Dealer\\src\\main\\resources\\xmls\\suppliers.xml";
    private static final String PART_XML_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Car Dealer\\src\\main\\resources\\xmls\\parts.xml";
    private static final String CAR_XML_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Car Dealer\\src\\main\\resources\\xmls\\cars.xml";
    private static final String CUSTOMER_XML_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Car Dealer\\src\\main\\resources\\xmls\\customers.xml";
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    @Autowired
    public AppController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileUtil fileUtil, XmlParser xmlParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public void run(String... args) throws Exception {
//        seedSuppliers();
//        seedParts();
//        seedCars();
//        seedCustomers();
//        seedSales();
//        printCustomersByBirthDate();
//        printToyotaCars();
//        printLocalSuppliers();
//        printCarsAndParts();
//        printCustomersTotalSales();
//        printSalesWithDiscounts();
    }

    private void seedSuppliers() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(SUPPLIER_XML_PATH);
        SupplierDtos supplierDtos = (SupplierDtos) this.xmlParser.parseXml(xml, SupplierDtos.class);
        this.supplierService.seedSuppliers(supplierDtos);
    }

    private void seedParts() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(PART_XML_PATH);
        PartDtos partDtos = (PartDtos) this.xmlParser.parseXml(xml, PartDtos.class);
        this.partService.seedParts(partDtos);
    }

    private void seedCars() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(CAR_XML_PATH);
        CarDtos carDtos = (CarDtos) this.xmlParser.parseXml(xml, CarDtos.class);
        this.carService.seedCars(carDtos);
    }

    private void seedCustomers() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(CUSTOMER_XML_PATH);
        CustomerDtos customerDtos = (CustomerDtos) this.xmlParser.parseXml(xml, CustomerDtos.class);
        this.customerService.seedCustomers(customerDtos);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void printCustomersByBirthDate() throws JAXBException {
        OrderedCustomersDtos customersDtos = this.customerService.getCustomersByBirthDate();
        System.out.println(this.xmlParser.parseObject(customersDtos, OrderedCustomersDtos.class));
    }

    private void printToyotaCars() throws JAXBException {
        ToyotaCarDtos toyotaCarDtos = this.carService.getToyotaCars();
        System.out.println(this.xmlParser.parseObject(toyotaCarDtos, ToyotaCarDtos.class));

    }

    private void printLocalSuppliers() throws JAXBException {
        LocalSupplierDtos localSupplierDtos = this.supplierService.getLocalSuppliers();
        System.out.println(this.xmlParser.parseObject(localSupplierDtos, LocalSupplierDtos.class));
    }

    private void printCarsAndParts() throws JAXBException {
        CarAndPartsDtos carAndPartsDtos = this.carService.getCarsAndParts();
        System.out.println(this.xmlParser.parseObject(carAndPartsDtos, CarAndPartsDtos.class));
    }

    private void printCustomersTotalSales() throws JAXBException {
        CustomerTotalSalesDtos customerTotalSalesDtos = this.customerService.getCustomersTotalSales();
        System.out.println(this.xmlParser.parseObject(customerTotalSalesDtos, CustomerTotalSalesDtos.class));
    }

    private void printSalesWithDiscounts() throws JAXBException {
        SaleDiscountsDtos saleDiscountsDtos = this.saleService.getSalesWithDiscount();
        System.out.println(this.xmlParser.parseObject(saleDiscountsDtos, SaleDiscountsDtos.class));
    }
}
