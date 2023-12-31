package com.khomenok.librarysystem.model.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private Long id;

    private String title;

    private String question;

    private String userEmail;

    private String adminEmail;

    private String response;

    private boolean closed;

    public MessageDTO() {
    }
}
