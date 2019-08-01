package com.example.demo.service;

import com.example.demo.domain.dto.CustomerDto;
import com.example.demo.domain.dto.queryDto.CustomerTotalSalesDto;
import com.example.demo.domain.dto.queryDto.OrderedCustomersDto;
import com.example.demo.domain.entity.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(CustomerDto[] customerDtos);

    OrderedCustomersDto[] getCustomersByBirthDate();

    CustomerTotalSalesDto[] getCustomersTotalSales();
}
