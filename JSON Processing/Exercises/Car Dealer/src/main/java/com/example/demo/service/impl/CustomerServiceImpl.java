package com.example.demo.service.impl;

import com.example.demo.domain.dto.CustomerDto;
import com.example.demo.domain.dto.queryDto.CustomerTotalSalesDto;
import com.example.demo.domain.dto.queryDto.OrderedCustomersDto;
import com.example.demo.domain.dto.queryDto.SaleDto;
import com.example.demo.domain.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.util.ValidatorUtil;
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
    public void seedCustomers(CustomerDto[] customerDtos) {
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
    public OrderedCustomersDto[] getCustomersByBirthDate() {
        List<Customer> customers = this.customerRepository.findAllByBirthDate();
        return customers.stream()
                .map(c -> {
                    OrderedCustomersDto customerDto = this.modelMapper.map(c, OrderedCustomersDto.class);
                    Set<SaleDto> saleDtos = Arrays.stream(this.modelMapper.map(c.getSales(), SaleDto[].class)).collect(Collectors.toSet());
                    customerDto.setSaleDtos(saleDtos);
                    return customerDto;
                }).toArray(OrderedCustomersDto[]::new);
    }

    @Override
    public CustomerTotalSalesDto[] getCustomersTotalSales() {
        return this.customerRepository.findAll().stream()
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
                }).toArray(CustomerTotalSalesDto[]::new);
    }
}
