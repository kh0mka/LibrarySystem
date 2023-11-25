package com.khomenok.librarysystem.model.dto;

import lombok.Data;

@Data
public class HistoryDTO {

    private SearchBookDTO book;

    private String checkoutDate;

    private String returnDate;

    public HistoryDTO() {
    }
}
