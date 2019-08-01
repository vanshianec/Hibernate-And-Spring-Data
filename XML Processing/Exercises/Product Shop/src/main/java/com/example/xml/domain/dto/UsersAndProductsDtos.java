package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsDtos implements Serializable {

    @XmlElement(name = "user")
    private List<UsersAndProductsDto> dtos;

    public UsersAndProductsDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<UsersAndProductsDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<UsersAndProductsDto> dtos) {
        this.dtos = dtos;
    }
}
