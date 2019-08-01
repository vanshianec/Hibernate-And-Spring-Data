package com.example.demo.service;

import com.example.demo.domain.dto.SupplierDto;
import com.example.demo.domain.dto.queryDto.LocalSupplierDto;

public interface SupplierService {
    void seedSuppliers(SupplierDto[] supplierDtos);

    LocalSupplierDto[] getLocalSuppliers();
}
