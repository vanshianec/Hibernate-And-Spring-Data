package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsDtos implements Serializable {
    @XmlElement(name = "car")
    private List<CarAndPartsDto> dtos;

    public CarAndPartsDtos() {
        this.dtos = new ArrayList<>();
    }

    public List<CarAndPartsDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<CarAndPartsDto> dtos) {
        this.dtos = dtos;
    }
}
