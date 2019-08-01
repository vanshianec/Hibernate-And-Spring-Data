package com.example.xml.controller;

import com.example.xml.domain.dto.*;
import com.example.xml.service.CategoryService;
import com.example.xml.service.ProductService;
import com.example.xml.service.UserService;
import com.example.xml.util.FileUtil;
import com.example.xml.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final static String USER_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Product Shop\\src\\main\\resources\\xmls\\users.xml";
    private final static String CATEGORY_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Product Shop\\src\\main\\resources\\xmls\\categories.xml";
    private final static String PRODUCT_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\XML Processing\\Exercises\\Product Shop\\src\\main\\resources\\xmls\\products.xml";
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public AppController(FileUtil fileUtil, XmlParser xmlParser, UserService userService, CategoryService categoryService, ProductService productService) {
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
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
        printUsersAndProducts();
    }

    private void seedUsers() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(USER_XML_FILE_PATH);
        UserDtos userDtos = (UserDtos) xmlParser.parseXml(xml, UserDtos.class);
        this.userService.seedUsers(userDtos);
    }

    private void seedCategories() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(CATEGORY_XML_FILE_PATH);
        CategoryDtos categoryDtos = (CategoryDtos) this.xmlParser.parseXml(xml, CategoryDtos.class);
        this.categoryService.seedCategories(categoryDtos);
    }

    private void seedProducts() throws IOException, JAXBException {
        String xml = this.fileUtil.fileContent(PRODUCT_XML_FILE_PATH);
        ProductDtos productDtos = (ProductDtos) this.xmlParser.parseXml(xml, ProductDtos.class);
        this.productService.seedProducts(productDtos);
    }

    private void printProductsInPriceRange() throws JAXBException {
        //products in price range between 500 and 1000
        BigDecimal lowerBound = new BigDecimal("500");
        BigDecimal higherBound = new BigDecimal("1000");
        List<ProductSellerDto> dtos = this.productService.getProductsInRange(lowerBound, higherBound);
        ProductSellerDtos productSellerDtos = new ProductSellerDtos();
        productSellerDtos.setProductDtos(dtos);
        System.out.println(this.xmlParser.parseObject(productSellerDtos, ProductSellerDtos.class));
    }

    private void printUsersSoldProducts() throws JAXBException {
        UserSoldProductsDtos userSoldProductsDtos = this.userService.getUsersSoldProducts();
        System.out.println(this.xmlParser.parseObject(userSoldProductsDtos, UserSoldProductsDtos.class));
    }

    private void printCategoriesByProducts() throws JAXBException {
        List<CategoriesByProductsDto> categoriesByProductsDtos = this.categoryService.getCategoriesByProducts();
        CategoriesByProductsDtos categories = new CategoriesByProductsDtos();
        categories.setDtos(categoriesByProductsDtos);
        System.out.println(this.xmlParser.parseObject(categories, CategoriesByProductsDtos.class));
    }

    private void printUsersAndProducts() throws JAXBException {
        UsersAndProductsDtos usersAndProductsDtos = this.userService.getUsersAndProducts();
        System.out.println(this.xmlParser.parseObject(usersAndProductsDtos, UsersAndProductsDtos.class));
    }
}
