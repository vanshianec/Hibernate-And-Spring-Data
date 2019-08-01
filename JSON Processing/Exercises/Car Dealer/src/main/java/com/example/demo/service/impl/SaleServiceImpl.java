package com.example.demo.service.impl;

import com.example.demo.domain.dto.CarDto;
import com.example.demo.domain.dto.queryDto.SaleDiscountsDto;
import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.Sale;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Transactional
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        List<Car> cars = this.carRepository.findAll();
        for (Car car : cars) {
            Customer customer = getRandomCustomer();
            int discount = getRandomDiscount();
            Sale sale = new Sale();
            sale.setCar(car);
            sale.setCustomer(customer);
            sale.setDiscount(discount);
            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public SaleDiscountsDto[] getSalesWithDiscount() {
        return this.saleRepository.findAll().stream()
                .map(s -> {
                    SaleDiscountsDto saleDto = this.modelMapper.map(s, SaleDiscountsDto.class);
                    BigDecimal price = s.getCar().getParts().stream()
                            .map(p -> p.getPrice())
                            .reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2));
                    BigDecimal discountPrice = price.multiply(BigDecimal.valueOf(s.getDiscount() * 0.01));
                    BigDecimal priceWithDiscount = price.subtract(discountPrice);
                    saleDto.setPrice(price);
                    saleDto.setPriceWithDiscount(priceWithDiscount);
                    return saleDto;
                }).toArray(SaleDiscountsDto[]::new);
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        int id = random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(id);
    }

    private int getRandomDiscount() {
        Random random = new Random();
        final int[] discounts = {0, 5, 10, 15, 20, 30, 40, 50};
        return discounts[random.nextInt(discounts.length)];
    }
}
