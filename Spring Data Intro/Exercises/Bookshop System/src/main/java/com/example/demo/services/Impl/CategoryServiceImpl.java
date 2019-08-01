package com.example.demo.services.Impl;

import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Spring Data Intro\\Exercises\\Bookshop System\\src\\main\\resources\\files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        for (String categoryName : categories) {
            Category category = new Category();
            category.setName(categoryName);
            this.categoryRepository.saveAndFlush(category);
        }


    }
}
