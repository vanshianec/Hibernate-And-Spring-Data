package com.example.demo.service.impl;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductSellerDto;
import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProducts(ProductDto[] productDtos) {
        for (ProductDto productDto : productDtos) {
            if (!validatorUtil.isValid(productDto)) {
                validatorUtil.violations(productDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Product product = this.modelMapper.map(productDto, Product.class);
            product.setBuyer(getRandomBuyer());
            product.setSeller(getRandomSeller());
            product.setCategories(getRandomCategories());
            this.productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<ProductSellerDto> getProductsInRange(BigDecimal lowerBound, BigDecimal higherBound) {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(lowerBound, higherBound);
        return Arrays.stream(this.modelMapper.map(products, ProductSellerDto[].class)).collect(Collectors.toList());
    }


    private User getRandomBuyer() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count()) + 1;
        if (id % 4 == 0) {
            return null;
        }
        return this.userRepository.findById(id);
    }

    private User getRandomSeller() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count()) + 1;
        return this.userRepository.findById(id);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int size = random.nextInt((int) this.categoryRepository.count()) + 1;
        for (int i = 0; i < size; i++) {
            categories.add(getRandomCategory());
        }
        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();
        int id = random.nextInt((int) this.categoryRepository.count()) + 1;
        return this.categoryRepository.findById(id);
    }
}
