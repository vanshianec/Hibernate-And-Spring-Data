package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierDtos implements Serializable {
    @XmlElement(name = "supplier")
    private List<SupplierDto> dtos;

    public SupplierDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<SupplierDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<SupplierDto> dtos) {
        this.dtos = dtos;
    }
}
