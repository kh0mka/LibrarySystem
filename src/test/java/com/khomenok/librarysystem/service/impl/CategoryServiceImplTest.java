package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.errors.ObjectNotFoundException;
import com.khomenok.librarysystem.model.entity.Category;
import com.khomenok.librarysystem.model.enums.CategoryName;
import com.khomenok.librarysystem.repository.CategoryRepository;
import com.khomenok.librarysystem.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    private CategoryService serviceToTest;

    @BeforeEach
    void setUp() {
        serviceToTest = new CategoryServiceImpl(mockCategoryRepository);

    }

    @Test
    void testCategoryNotFound() {
        CategoryName validCategoryName = CategoryName.BIOGRAPHY;
        when(mockCategoryRepository.findByName(validCategoryName)).thenReturn(Optional.empty());
        assertThrows(
                ObjectNotFoundException.class,
                () -> serviceToTest.getCategory(validCategoryName)
        );
    }

    @Test
    void testCategoryFound() {
        CategoryName validCategoryName = CategoryName.BIOGRAPHY;
        Category foundCategory = new Category(validCategoryName);
        when(mockCategoryRepository.findByName(validCategoryName)).thenReturn(Optional.of(foundCategory));
        Category result = serviceToTest.getCategory(validCategoryName);
        assertEquals(foundCategory, result);
    }

    @Test
    void testSeedCategories() {
        when(mockCategoryRepository.count()).thenReturn(0L);
        serviceToTest.seedCategories();
        verify(mockCategoryRepository, times(1)).saveAll(anyList());
    }


}