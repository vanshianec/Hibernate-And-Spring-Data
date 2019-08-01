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
public class UserSoldProductsDtos implements Serializable {

    @XmlElement(name = "user")
    private List<UserSoldProductsDto> dtos;

    public UserSoldProductsDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<UserSoldProductsDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<UserSoldProductsDto> dtos) {
        this.dtos = dtos;
    }
}
