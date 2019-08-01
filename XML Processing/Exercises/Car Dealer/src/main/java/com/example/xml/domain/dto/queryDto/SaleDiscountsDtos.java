package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountsDtos implements Serializable {
    @XmlElement(name = "sale")
    private List<SaleDiscountsDto> dtos;

    public SaleDiscountsDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<SaleDiscountsDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<SaleDiscountsDto> dtos) {
        this.dtos = dtos;
    }
}
