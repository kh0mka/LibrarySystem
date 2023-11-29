package com.khomenok.librarysystem.event;

import com.khomenok.librarysystem.model.entity.Book;
import org.springframework.context.ApplicationEvent;

public class CheckoutCreatedEvent extends ApplicationEvent {
    private Book book;

    public CheckoutCreatedEvent(Object source) {
        super(source);
    }

    public Book getBook() {
        return book;
    }

    public CheckoutCreatedEvent setBook(Book book) {
        this.book = book;
        return this;
    }
}
