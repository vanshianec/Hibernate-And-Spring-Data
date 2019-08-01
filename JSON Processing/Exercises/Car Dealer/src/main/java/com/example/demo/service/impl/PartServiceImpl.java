package com.example.demo.service.impl;

import com.example.demo.domain.dto.PartDto;
import com.example.demo.domain.entity.Part;
import com.example.demo.domain.entity.Supplier;
import com.example.demo.repository.PartRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.PartService;
import com.example.demo.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public void seedParts(PartDto[] partDtos) {
        for (PartDto partDto : partDtos) {
            if (!validatorUtil.isValid(partDto)) {
                validatorUtil.printErrors(partDto);
                continue;
            }
            Part part = this.modelMapper.map(partDto, Part.class);
            part.setSupplier(randomSupplier());
            this.partRepository.saveAndFlush(part);
        }
    }

    private Supplier randomSupplier() {
        Random random = new Random();
        int id = random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.findById(id);
    }
}
