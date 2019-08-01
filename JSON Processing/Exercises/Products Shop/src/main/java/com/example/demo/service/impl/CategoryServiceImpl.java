package com.example.demo.service.impl;

import com.example.demo.domain.dto.CategoriesByProductsDto;
import com.example.demo.domain.dto.CategoryDto;
import com.example.demo.domain.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.util.ValidatorUtil;
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
    public void seedCategories(CategoryDto[] categoryDtos) {
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
