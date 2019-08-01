package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerDtos {
    @XmlElement(name = "customer")
    private List<CustomerDto> dtos;

    public CustomerDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<CustomerDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<CustomerDto> dtos) {
        this.dtos = dtos;
    }
}
