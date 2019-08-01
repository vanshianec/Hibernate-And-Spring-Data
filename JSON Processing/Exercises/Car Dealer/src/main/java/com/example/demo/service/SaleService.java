package com.example.demo.service;

import com.example.demo.domain.dto.queryDto.SaleDiscountsDto;

public interface SaleService {
    void seedSales();

    SaleDiscountsDto[] getSalesWithDiscount();
}
