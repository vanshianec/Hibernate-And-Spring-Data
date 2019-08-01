package com.example.demo.dto;

import java.math.BigDecimal;

public class EmployeeDto extends BaseDto {

    private BigDecimal salary;

    public EmployeeDto() {
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(final BigDecimal salary) {
        this.salary = salary;
    }
}
