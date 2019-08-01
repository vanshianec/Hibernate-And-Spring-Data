package com.example.xml.domain.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsDto implements Serializable {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private Set<SoldProductsDto> soldProductsDtoSet;

    public UserSoldProductsDto() {
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
