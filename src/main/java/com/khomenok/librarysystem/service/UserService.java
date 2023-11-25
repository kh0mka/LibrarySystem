package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.dto.UserRegisterDTO;
import com.khomenok.librarysystem.model.entity.User;

import java.util.List;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    void saveUser(User user);

    List<User> findAllUsers();

    User getUser(Long id);

    void deleteUser(Long id);

    User getUser(String email);
}
