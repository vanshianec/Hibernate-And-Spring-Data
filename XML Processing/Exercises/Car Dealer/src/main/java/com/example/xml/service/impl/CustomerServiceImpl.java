package com.example.xml.service.impl;

import com.example.xml.domain.dto.CustomerDto;
import com.example.xml.domain.dto.CustomerDtos;
import com.example.xml.domain.dto.queryDto.*;
import com.example.xml.domain.entity.Customer;
import com.example.xml.repository.CustomerRepository;
import com.example.xml.service.CustomerService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public void seedCustomers(CustomerDtos dtos) {
        List<CustomerDto> customerDtos = dtos.getDtos();
        for (CustomerDto customerDto : customerDtos) {
            if (!validatorUtil.isValid(customerDto)) {
                validatorUtil.printErrors(customerDto);
                continue;
            }
            Customer customer = modelMapper.map(customerDto, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public OrderedCustomersDtos getCustomersByBirthDate() {
        List<Customer> customers = this.customerRepository.findAllByBirthDate();
        OrderedCustomersDtos orderedCustomersDtos = new OrderedCustomersDtos();
        List<OrderedCustomersDto> dtos = customers.stream()
                .map(c -> {
                    OrderedCustomersDto customerDto = this.modelMapper.map(c, OrderedCustomersDto.class);
                    Set<SaleDto> saleDtos = Arrays.stream(this.modelMapper.map(c.getSales(), SaleDto[].class)).collect(Collectors.toSet());
                    customerDto.setSaleDtos(saleDtos);
                    return customerDto;
                }).collect(Collectors.toList());
        orderedCustomersDtos.setDtos(dtos);
        return orderedCustomersDtos;
    }

    @Override
    public CustomerTotalSalesDtos getCustomersTotalSales() {
        CustomerTotalSalesDtos customerTotalSalesDtos = new CustomerTotalSalesDtos();
        List<CustomerTotalSalesDto> dtos = this.customerRepository.findAll().stream()
                .map(c -> {
                    CustomerTotalSalesDto customerDto = this.modelMapper.map(c, CustomerTotalSalesDto.class);
                    customerDto.setBoughtCars(c.getSales().size());
                    BigDecimal moneySpent = c.getSales().stream()
                            .map(sale -> sale.getCar().getParts().stream()
                                    .map(p -> p.getPrice())
                                    .reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2)))
                            .reduce(BigDecimal.ZERO, ((e1, e2) -> e1.add(e2)));
                    customerDto.setSpentMoney(moneySpent);
                    return customerDto;
                }).collect(Collectors.toList());
        customerTotalSalesDtos.setDtos(dtos);
        return customerTotalSalesDtos;
    }
}
