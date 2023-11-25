package com.khomenok.librarysystem.model.entity;

import com.khomenok.librarysystem.model.enums.CategoryName;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryName name;

    public Category(CategoryName name) {
        this.name = name;
    }

    public Category() {
    }
}
