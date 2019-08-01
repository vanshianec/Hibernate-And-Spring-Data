package com.example.demo.domain.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Set;

public class UsersAndProductsDto implements Serializable {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private Set<UserAndSoldProductsDto> soldProducts;

    public UsersAndProductsDto(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<UserAndSoldProductsDto> getSoldProductsDtos() {
        return soldProducts;
    }

    public void setSoldProductsDtos(Set<UserAndSoldProductsDto> soldProductsDtos) {
        this.soldProducts = soldProductsDtos;
    }
}
