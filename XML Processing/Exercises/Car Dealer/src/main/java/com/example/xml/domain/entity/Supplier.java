package com.example.xml.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    private String name;
    private boolean isImporter;
    private Set<Part> supplierParts;

    public Supplier() {
        this.supplierParts = new HashSet<>();
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_importer")
    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    @OneToMany(mappedBy = "supplier")
    public Set<Part> getSupplierParts() {
        return supplierParts;
    }

    public void setSupplierParts(Set<Part> supplierParts) {
        this.supplierParts = supplierParts;
    }
}
