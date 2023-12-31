package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.dto.CheckOutDTO;

import java.util.List;

public interface CheckoutService {


    void checkoutBook(Long id, String email);

    List<CheckOutDTO> getUserCheckouts(Long id);

    void returnBook(Long id, String email);

    void renewCheckout(Long id, String email);

    boolean bookAlreadyCheckedOutByUser(Long bookId, String email);

    int getLoansCount(String email);

    boolean isUserBlocked(String email);

    void deleteBookCheckouts(Long id);


    void checkIfUserHasBook(String userEmail, Long bookId);
}
