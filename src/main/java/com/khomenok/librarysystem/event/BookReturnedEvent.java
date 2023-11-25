package com.khomenok.librarysystem.event;

import com.khomenok.librarysystem.model.entity.Checkout;
import org.springframework.context.ApplicationEvent;

public class BookReturnedEvent extends ApplicationEvent {

    private Checkout checkout;


    public BookReturnedEvent(Object source) {
        super(source);
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public BookReturnedEvent setCheckout(Checkout checkout) {
        this.checkout = checkout;
        return this;
    }
}
