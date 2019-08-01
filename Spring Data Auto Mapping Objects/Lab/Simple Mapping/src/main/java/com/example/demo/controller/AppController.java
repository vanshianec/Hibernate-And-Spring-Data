package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.ManagerDto;
import com.example.demo.entities.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Employee manager = new Employee() {{
            setFirstName("Ivan");
            setLastName("Ivanov");
            setSalary(new BigDecimal("10000"));
        }};

        Employee employee = new Employee() {{
            setFirstName("Pesho");
            setLastName("Peshov");
            setSalary(new BigDecimal("4000"));
        }};
        Employee employee1 = new Employee() {{
            setFirstName("Gosho");
            setLastName("Goshov");
            setSalary(new BigDecimal("5000"));
        }};
        Employee employee2 = new Employee() {{
            setFirstName("Misho");
            setLastName("Mishov");
            setSalary(new BigDecimal("3000"));
        }};
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee1);
        employees.add(employee2);
        employee.setManager(manager);
        employee1.setManager(manager);
        employee2.setManager(manager);
        manager.setEmployees(employees);

        ModelMapper mapper = new ModelMapper();
        ManagerDto managerDto = mapper.map(manager, ManagerDto.class);
        System.out.printf("%s %s | Employees: %d%n", managerDto.getFirstName(), managerDto.getLastName(), managerDto.getEmployees().size());
        managerDto.getEmployees().forEach(e -> System.out.printf("\t-%s %s %.2f%n", e.getFirstName(), e.getLastName(), e.getSalary()));
    }
}
