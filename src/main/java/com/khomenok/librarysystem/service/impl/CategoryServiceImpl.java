package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.errors.ObjectNotFoundException;
import com.khomenok.librarysystem.model.entity.Category;
import com.khomenok.librarysystem.model.enums.CategoryName;
import com.khomenok.librarysystem.repository.CategoryRepository;
import com.khomenok.librarysystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() {
        if (this.categoryRepository.count() == 0) {
            List<Category> categories = Arrays.stream(CategoryName.values())
                    .map(Category::new)
                    .collect(Collectors.toList());
            this.categoryRepository.saveAll(categories);
        }
    }

    @Override
    public Category getCategory(CategoryName category) {
        //TODO: handled
        checkCategoryNameAvailable(String.valueOf(category));
        Optional<Category> optionalCategory = this.categoryRepository.findByName(category);
        if (optionalCategory.isEmpty()) {
            throw new ObjectNotFoundException("category " + category  + " not found");
        }
         return optionalCategory.get();
    }

    public void checkCategoryNameAvailable(String categoryName) {
        List<String> categoryNames = Arrays
                .stream(CategoryName.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        if (!categoryNames.contains(categoryName.toUpperCase())) {
            throw new ObjectNotFoundException("category " + categoryName + " not found");
        }
    }
}
