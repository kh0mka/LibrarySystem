package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.entity.Category;
import com.khomenok.librarysystem.model.enums.CategoryName;

public interface CategoryService {
    void seedCategories();

    Category getCategory(CategoryName category);

    void checkCategoryNameAvailable(String categoryName);
}
