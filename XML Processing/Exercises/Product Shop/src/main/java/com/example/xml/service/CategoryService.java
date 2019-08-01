package com.example.xml.service;

import com.example.xml.domain.dto.CategoriesByProductsDto;
import com.example.xml.domain.dto.CategoryDtos;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategoryDtos categoryDtos);

    List<CategoriesByProductsDto> getCategoriesByProducts();
}
