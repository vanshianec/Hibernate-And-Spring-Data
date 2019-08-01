package com.example.xml.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSellerDtos implements Serializable {

    @XmlElement(name = "product")
    private List<ProductSellerDto> productDtos;

    public ProductSellerDtos() {
        this.productDtos = new ArrayList<>();
    }

    public List<ProductSellerDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductSellerDto> productDtos) {
        this.productDtos = productDtos;
    }
}
