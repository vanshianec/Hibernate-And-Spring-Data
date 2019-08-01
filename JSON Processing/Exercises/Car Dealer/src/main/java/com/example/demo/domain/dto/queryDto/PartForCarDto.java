package com.example.demo.domain.dto.queryDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PartForCarDto {
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("Price")
    private BigDecimal price;

    public PartForCarDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
