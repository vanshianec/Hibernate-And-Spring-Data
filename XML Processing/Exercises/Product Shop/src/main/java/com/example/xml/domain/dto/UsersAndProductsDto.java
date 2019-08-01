package com.example.xml.domain.dto;


import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsDto implements Serializable {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "age")
    private int age;
    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
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
