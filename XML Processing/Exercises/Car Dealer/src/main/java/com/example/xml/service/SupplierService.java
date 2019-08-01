package com.example.xml.service;

import com.example.xml.domain.dto.SupplierDtos;
import com.example.xml.domain.dto.queryDto.LocalSupplierDto;
import com.example.xml.domain.dto.queryDto.LocalSupplierDtos;

public interface SupplierService {
    void seedSuppliers(SupplierDtos supplierDtos);

    LocalSupplierDtos getLocalSuppliers();
}
