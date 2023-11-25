package com.khomenok.librarysystem.repository;

import com.khomenok.librarysystem.model.entity.Category;
import com.khomenok.librarysystem.model.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryName name);
}
