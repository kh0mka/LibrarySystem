package com.khomenok.librarysystem.service;



import com.khomenok.librarysystem.event.BookReturnedEvent;
import com.khomenok.librarysystem.event.CheckoutCreatedEvent;
import com.khomenok.librarysystem.model.dto.AddBookDTO;
import com.khomenok.librarysystem.model.dto.BookDTO;
import com.khomenok.librarysystem.model.dto.SearchBookDTO;
import com.khomenok.librarysystem.model.entity.Book;

import java.util.List;

public interface BookService {
    List<SearchBookDTO> getSearchedBooks();

    List<BookDTO> getAllBooks();

    SearchBookDTO getSearchBookDTO(Long id);

    List<SearchBookDTO> getBooksByTitle(String title);
    List<SearchBookDTO> getBooksByCategory(String category);

    List<SearchBookDTO> getBooksByTitleAndCategory(String title, String category);

    BookDTO getBookDTO(Long id);

    Book getBook(Long id);


    void registerBook(AddBookDTO addBookDTO);


    void deleteBook(Long id);


    void decreaseCopiesAvailable(CheckoutCreatedEvent checkoutCreatedEvent);

    void increaseCopiesAvailable(BookReturnedEvent bookReturnedEvent);

    void saveBook(Book book);
}
