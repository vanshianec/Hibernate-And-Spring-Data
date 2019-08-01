package com.example.xml.service;

import com.example.xml.domain.dto.queryDto.SaleDiscountsDtos;

public interface SaleService {
    void seedSales();

    SaleDiscountsDtos getSalesWithDiscount();
}
