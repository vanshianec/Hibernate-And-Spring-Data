package com.example.demo.domain.dto.queryDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocalSupplierDto implements Serializable {

    @Expose
    @SerializedName("Id")
    private int id;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("partsCount")
    private int partsCount;

    public LocalSupplierDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
