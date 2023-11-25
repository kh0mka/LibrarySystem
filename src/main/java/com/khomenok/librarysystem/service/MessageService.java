package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.model.dto.MessageDTO;
import com.khomenok.librarysystem.model.dto.PostMessageDTO;
import com.khomenok.librarysystem.model.entity.Message;
import com.khomenok.librarysystem.model.entity.User;

import java.util.List;

public interface MessageService {
    void registerMessage(PostMessageDTO postMessageDTO, String userEmail);

    List<MessageDTO> getUsersMessages(String userEmail);

    List<MessageDTO> getOpenMessages();

    Message getMessage(Long id);

    void answerMessage(Long messageId, String response, User admin);

    void deleteMessage(Long id);

    void deleteUserMessages(Long userId);
}
