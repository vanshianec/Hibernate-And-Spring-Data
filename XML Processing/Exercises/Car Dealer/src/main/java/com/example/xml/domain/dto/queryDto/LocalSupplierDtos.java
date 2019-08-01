package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSupplierDtos implements Serializable {
    @XmlElement(name = "supplier")
    private List<LocalSupplierDto> dtos;

    public LocalSupplierDtos(){
        this.dtos = new ArrayList<>();
    }

    public List<LocalSupplierDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<LocalSupplierDto> dtos) {
        this.dtos = dtos;
    }
}
