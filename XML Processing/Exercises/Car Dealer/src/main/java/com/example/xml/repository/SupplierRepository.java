package com.example.xml.repository;

import com.example.xml.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findById(int id);

    List<Supplier> findAllByImporterFalse();
}
