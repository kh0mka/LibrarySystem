package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.dto.LeaveReviewDTO;
import com.khomenok.librarysystem.model.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getByBook(Long bookId);

    boolean reviewLeft(String userEmail, Long bookId);

    void registerReview(LeaveReviewDTO leaveReviewDTO, String userEmail, Long bookId);

    void deleteBookReviews(Long id);

    void deleteUserReviews(Long userId);
}
