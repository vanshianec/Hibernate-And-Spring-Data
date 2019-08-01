package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "addresses")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressesDto implements Serializable {
    @XmlElement(name = "address")
    private List<AddressDto> addressDtos;

    public AddressesDto(){
        this.addressDtos = new ArrayList<>();
    }

    public List<AddressDto> getAddressDtos() {
        return addressDtos;
    }

    public void setAddressDtos(List<AddressDto> addressDtos) {
        this.addressDtos = addressDtos;
    }
}
