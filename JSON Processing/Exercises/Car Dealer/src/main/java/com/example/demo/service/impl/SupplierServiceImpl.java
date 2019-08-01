package com.example.demo.service.impl;

import com.example.demo.domain.dto.SupplierDto;
import com.example.demo.domain.dto.queryDto.LocalSupplierDto;
import com.example.demo.domain.entity.Supplier;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.SupplierService;
import com.example.demo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void seedSuppliers(SupplierDto[] supplierDtos) {
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
    public LocalSupplierDto[] getLocalSuppliers() {
        return this.supplierRepository.findAllByImporterFalse().stream()
                .map(s -> {
                    LocalSupplierDto localSupplierDto = this.modelMapper.map(s, LocalSupplierDto.class);
                    localSupplierDto.setPartsCount(s.getSupplierParts().size());
                    return localSupplierDto;
                }).toArray(LocalSupplierDto[]::new);
    }
}
