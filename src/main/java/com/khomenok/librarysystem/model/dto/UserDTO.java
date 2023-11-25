package com.khomenok.librarysystem.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String roles;

    private boolean isActive;

    private boolean isAdmin;

    public UserDTO() {
    }
}
