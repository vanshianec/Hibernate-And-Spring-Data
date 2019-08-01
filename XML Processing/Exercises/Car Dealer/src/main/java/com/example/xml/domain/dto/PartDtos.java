package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartDtos {
    @XmlElement(name = "part")
    private List<PartDto> dtos;

    public PartDtos(){
        this.dtos = new ArrayList<>();
    }

    public List<PartDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<PartDto> dtos) {
        this.dtos = dtos;
    }
}
