package com.example.xml.service;

import com.example.xml.domain.dto.CustomerDtos;
import com.example.xml.domain.dto.queryDto.CustomerTotalSalesDtos;
import com.example.xml.domain.dto.queryDto.OrderedCustomersDtos;

public interface CustomerService {
    void seedCustomers(CustomerDtos customerDtos);

    OrderedCustomersDtos getCustomersByBirthDate();

    CustomerTotalSalesDtos getCustomersTotalSales();
}
