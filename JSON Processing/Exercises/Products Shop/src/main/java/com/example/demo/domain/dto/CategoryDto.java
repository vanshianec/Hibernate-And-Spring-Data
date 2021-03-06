package com.example.demo.domain.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategoryDto implements Serializable {

    @Expose
    private String name;

    public CategoryDto() {
    }

    @Size(min = 3, max = 15, message = "Name should be between 3 and 15 symbols")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
