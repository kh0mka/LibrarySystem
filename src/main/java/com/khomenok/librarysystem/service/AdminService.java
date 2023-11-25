package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.dto.AddBookDTO;
import com.khomenok.librarysystem.model.dto.MessageDTO;
import com.khomenok.librarysystem.model.dto.MessageResponseDTO;
import com.khomenok.librarysystem.model.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<MessageDTO> getOpenMessages();

    void sendResponse(Long messageId, MessageResponseDTO messageResponseDTO, String email);

    void postBook(AddBookDTO addBookDTO);

    void increaseBookQuantity(Long id);

    void decreaseBookQuantity(Long id);

    void deleteBook(Long id);

    List<UserDTO> getAllUsersExceptPrincipal(String email);

    void addAdmin(Long id);

    void removeAdmin(Long id, String email);

    String getUserEmail(Long id);

    void deleteUser(Long id);
}
