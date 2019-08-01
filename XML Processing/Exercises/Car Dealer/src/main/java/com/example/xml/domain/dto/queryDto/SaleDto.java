package com.example.xml.domain.dto.queryDto;

import java.io.Serializable;

public class SaleDto implements Serializable {
    private int discount;
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
