package com.example.demo.domain.dto.queryDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderedCustomersDto implements Serializable {
    @Expose
    @SerializedName("Id")
    private int id;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("BirthDate")
    private Date birthDate;
    @Expose
    @SerializedName("IsYoungDriver")
    private boolean isYoungDriver;
    @Expose
    @SerializedName("Sales")
    private Set<SaleDto> saleDtos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderedCustomersDto(){
        this.saleDtos = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SaleDto> getSaleDtos() {
        return saleDtos;
    }

    public void setSaleDtos(Set<SaleDto> saleDtos) {
        this.saleDtos = saleDtos;
    }
}
