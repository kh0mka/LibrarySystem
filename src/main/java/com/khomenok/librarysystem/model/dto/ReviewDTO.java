package com.khomenok.librarysystem.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {

    private double rating;

    private String comment;

    private String userEmail;


    private LocalDate date;

    public ReviewDTO() {
    }
}
