package com.example.xml.service;

import com.example.xml.domain.dto.AddressDto;
import com.example.xml.domain.entities.Address;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressDtos();
}
