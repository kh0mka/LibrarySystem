package com.khomenok.librarysystem.repository;

import com.khomenok.librarysystem.model.entity.Book;
import com.khomenok.librarysystem.model.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategoryName(CategoryName name);

    List<Book> findAllByCategoryNameAndTitleContaining(CategoryName category, String title);

    List<Book> findByTitleContaining(String title);
}
