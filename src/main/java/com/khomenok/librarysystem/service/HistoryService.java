package com.khomenok.librarysystem.service;

import com.khomenok.librarysystem.event.BookReturnedEvent;
import com.khomenok.librarysystem.model.dto.HistoryDTO;

import java.util.List;

public interface HistoryService {
    List<HistoryDTO> getUserHistories(String email);

    void registerHistory(BookReturnedEvent bookReturnedEvent);

    void deleteBookHistories(Long id);

    void deleteUserHistories(Long userId);
}
