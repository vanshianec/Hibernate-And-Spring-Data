package com.example.xml.service.impl;

import com.example.xml.domain.dto.AddressDto;
import com.example.xml.repository.AddressRepository;
import com.example.xml.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AddressDto> getAddressDtos() {
        return this.addressRepository.findAll().stream().map(a -> this.modelMapper.map(a, AddressDto.class)).collect(Collectors.toList());
    }
}
