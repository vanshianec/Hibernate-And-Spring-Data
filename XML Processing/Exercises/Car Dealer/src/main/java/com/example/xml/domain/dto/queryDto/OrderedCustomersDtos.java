package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomersDtos {

    @XmlElement(name = "customer")
    private List<OrderedCustomersDto> dtos;

    public OrderedCustomersDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<OrderedCustomersDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<OrderedCustomersDto> dtos) {
        this.dtos = dtos;
    }
}
