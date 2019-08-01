package com.example.demo.domain.dto.queryDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaleDto implements Serializable {
    @Expose
    @SerializedName("Discount")
    private int discount;
    @Expose
    @SerializedName("Car")
    private SaleCarDto car;

    public SaleDto() {
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public SaleCarDto getCar() {
        return car;
    }

    public void setCar(SaleCarDto car) {
        this.car = car;
    }
}
