package com.example.xml.service;

import com.example.xml.domain.dto.ProductDto;
import com.example.xml.domain.dto.ProductDtos;
import com.example.xml.domain.dto.ProductSellerDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductDtos productDtos);
    List<ProductSellerDto> getProductsInRange(BigDecimal lowerBound, BigDecimal higherBound);
}
