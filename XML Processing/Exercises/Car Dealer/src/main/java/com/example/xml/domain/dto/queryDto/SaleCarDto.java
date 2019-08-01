package com.example.xml.domain.dto.queryDto;


import java.io.Serializable;

public class SaleCarDto implements Serializable {
    private String make;
    private String model;
    private long travelledDistance;

    public SaleCarDto(){}

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
