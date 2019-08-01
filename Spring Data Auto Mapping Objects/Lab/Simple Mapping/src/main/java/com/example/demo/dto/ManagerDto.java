package com.example.demo.dto;

import java.util.List;

public class ManagerDto extends BaseDto{

    private List<EmployeeDto> employees;

    public ManagerDto() {
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
