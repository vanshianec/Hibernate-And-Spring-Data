package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesByProductsDtos implements Serializable {

    @XmlElement(name = "category")
    private List<CategoriesByProductsDto> dtos;

    public CategoriesByProductsDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<CategoriesByProductsDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<CategoriesByProductsDto> dtos) {
        this.dtos = dtos;
    }
}
