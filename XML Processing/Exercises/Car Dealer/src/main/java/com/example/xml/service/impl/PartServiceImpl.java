package com.example.xml.service.impl;

import com.example.xml.domain.dto.PartDto;
import com.example.xml.domain.dto.PartDtos;
import com.example.xml.domain.entity.Part;
import com.example.xml.domain.entity.Supplier;
import com.example.xml.repository.PartRepository;
import com.example.xml.repository.SupplierRepository;
import com.example.xml.service.PartService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void seedParts(PartDtos dtos) {
        List<PartDto> partDtos = dtos.getDtos();
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
