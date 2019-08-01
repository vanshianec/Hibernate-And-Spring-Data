package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDtos implements Serializable {
    @XmlElement(name = "car")
    private List<CarDto> dtos;

    public CarDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<CarDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<CarDto> dtos) {
        this.dtos = dtos;
    }
}
