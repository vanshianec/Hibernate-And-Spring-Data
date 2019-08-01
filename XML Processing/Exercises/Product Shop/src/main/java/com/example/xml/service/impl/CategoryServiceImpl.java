package com.example.xml.service.impl;

import com.example.xml.domain.dto.CategoriesByProductsDto;
import com.example.xml.domain.dto.CategoryDto;
import com.example.xml.domain.dto.CategoryDtos;
import com.example.xml.domain.entities.Category;
import com.example.xml.repository.CategoryRepository;
import com.example.xml.service.CategoryService;
import com.example.xml.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategoryDtos dtos) {
        List<CategoryDto> categoryDtos = dtos.getCategoryDtos();
        for (CategoryDto categoryDto : categoryDtos) {
            if (!validatorUtil.isValid(categoryDto)) {
                validatorUtil.violations(categoryDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            Category category = this.modelMapper.map(categoryDto, Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public List<CategoriesByProductsDto> getCategoriesByProducts() {
        return this.categoryRepository.getCategoriesByProducts();
    }
}
