package com.example.xml.service.impl;

import com.example.xml.domain.dto.SupplierDto;
import com.example.xml.domain.dto.SupplierDtos;
import com.example.xml.domain.dto.queryDto.LocalSupplierDto;
import com.example.xml.domain.dto.queryDto.LocalSupplierDtos;
import com.example.xml.domain.entity.Supplier;
import com.example.xml.repository.SupplierRepository;
import com.example.xml.service.SupplierService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedSuppliers(SupplierDtos dtos) {
        List<SupplierDto> supplierDtos = dtos.getDtos();
        for (SupplierDto supplierDto : supplierDtos) {
            if (!validatorUtil.isValid(supplierDto)) {
                validatorUtil.printErrors(supplierDto);
                continue;
            }
            Supplier supplier = this.modelMapper.map(supplierDto, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
        }
    }

    @Override
    public LocalSupplierDtos getLocalSuppliers() {
        LocalSupplierDtos localSupplierDtos = new LocalSupplierDtos();
        List<LocalSupplierDto> dtos = this.supplierRepository.findAllByImporterFalse().stream()
                .map(s -> {
                    LocalSupplierDto localSupplierDto = this.modelMapper.map(s, LocalSupplierDto.class);
                    localSupplierDto.setPartsCount(s.getSupplierParts().size());
                    return localSupplierDto;
                }).collect(Collectors.toList());
        localSupplierDtos.setDtos(dtos);
        return localSupplierDtos;
    }
}
