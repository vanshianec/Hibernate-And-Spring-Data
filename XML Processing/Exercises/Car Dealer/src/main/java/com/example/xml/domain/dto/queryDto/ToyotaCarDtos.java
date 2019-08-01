package com.example.xml.domain.dto.queryDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToyotaCarDtos {
    @XmlElement(name = "car")
    private List<ToyotaCarDto> dtos;

    public ToyotaCarDtos(){
        this.dtos = new ArrayList<>();
    }

    public List<ToyotaCarDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<ToyotaCarDto> dtos) {
        this.dtos = dtos;
    }
}
