package com.example.demo.service;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductSellerDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductDto[] productDtos);
    List<ProductSellerDto> getProductsInRange(BigDecimal lowerBound, BigDecimal higherBound);
}
