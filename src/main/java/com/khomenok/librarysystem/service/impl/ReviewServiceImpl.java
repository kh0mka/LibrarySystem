package com.khomenok.librarysystem.service.impl;

import com.khomenok.librarysystem.errors.NotAllowedException;
import com.khomenok.librarysystem.model.dto.LeaveReviewDTO;
import com.khomenok.librarysystem.model.dto.ReviewDTO;
import com.khomenok.librarysystem.model.entity.Book;
import com.khomenok.librarysystem.model.entity.Review;
import com.khomenok.librarysystem.model.entity.User;
import com.khomenok.librarysystem.repository.ReviewRepository;
import com.khomenok.librarysystem.service.BookService;
import com.khomenok.librarysystem.service.CheckoutService;
import com.khomenok.librarysystem.service.ReviewService;
import com.khomenok.librarysystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    private UserService userService;

    private BookService bookService;

    private CheckoutService checkoutService;

    private ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService,
                             BookService bookService, CheckoutService checkoutService,
                             ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.checkoutService = checkoutService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDTO> getByBook(Long bookId) {

       List<Review> reviews = this.reviewRepository.findAllByBookIdOrderByDateDesc(bookId);
     return reviews.stream().map(r -> modelMapper.map(r, ReviewDTO.class))
               .collect(Collectors.toList());


    }

    @Override
    public boolean reviewLeft(String userEmail, Long bookId) {
        Optional<Review> optionalReview = this.reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (optionalReview.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void registerReview(LeaveReviewDTO leaveReviewDTO, String userEmail, Long bookId) {

        checkBooKExisting(bookId);

        if (reviewLeft(userEmail, bookId)) {
            throw new NotAllowedException();
        }

        this.checkoutService.checkIfUserHasBook(userEmail, bookId);

        Review review = modelMapper.map(leaveReviewDTO, Review.class);
        User user = this.userService.getUser(userEmail);
        Book book = this.bookService.getBook(bookId);
        review.setBook(book);
        review.setUser(user);
        review.setDate(LocalDate.now());
        this.reviewRepository.save(review);
    }

    private void checkBooKExisting(Long bookId) {
        this.bookService.getBook(bookId);
    }

    @Override
    public void deleteBookReviews(Long id) {
        this.reviewRepository.deleteAllByBookId(id);
    }

    @Override
    public void deleteUserReviews(Long userId) {
        this.reviewRepository.deleteAllByUserId(userId);
    }
}
