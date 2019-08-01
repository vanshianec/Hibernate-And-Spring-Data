package com.example.demo.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class UserSoldProductsDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    @SerializedName("soldProducts")
    private Set<SoldProductsDto> soldProductsDtoSet;

    public UserSoldProductsDto(){
        this.soldProductsDtoSet = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Length(min = 3)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<SoldProductsDto> getSoldProductsDtoSet() {
        return soldProductsDtoSet;
    }

    public void setSoldProductsDtoSet(Set<SoldProductsDto> soldProductsDtoSet) {
        this.soldProductsDtoSet = soldProductsDtoSet;
    }
}
