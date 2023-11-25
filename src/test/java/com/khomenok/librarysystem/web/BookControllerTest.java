package com.khomenok.librarysystem.web;

import com.khomenok.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookControllerTest {

    @Autowired
    private BookService bookService;

}