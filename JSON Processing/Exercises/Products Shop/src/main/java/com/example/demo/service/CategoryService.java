package com.example.demo.service;

import com.example.demo.domain.dto.CategoriesByProductsDto;
import com.example.demo.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategoryDto[] categoryDtos);

    List<CategoriesByProductsDto> getCategoriesByProducts();
}
