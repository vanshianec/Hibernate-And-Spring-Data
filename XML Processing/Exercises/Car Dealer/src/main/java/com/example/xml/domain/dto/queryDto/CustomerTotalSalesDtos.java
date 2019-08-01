package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesDtos implements Serializable {
    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> dtos;

    public CustomerTotalSalesDtos(){
        this.dtos = new ArrayList<>();
    }

    public List<CustomerTotalSalesDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<CustomerTotalSalesDto> dtos) {
        this.dtos = dtos;
    }
}
