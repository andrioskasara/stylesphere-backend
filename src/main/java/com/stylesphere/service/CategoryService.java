package com.stylesphere.service;

import com.stylesphere.model.Category;
import com.stylesphere.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(CategoryDto categoryDto);
}
