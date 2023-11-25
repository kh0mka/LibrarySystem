package com.khomenok.librarysystem.util;

import com.khomenok.librarysystem.model.dto.CheckOutDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimeConverter {

    public static long getTimeDifference(CheckOutDTO checkout) {



        LocalDate returnDate = checkout.getReturnDate();
        LocalDate nowDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(nowDate, returnDate);

        return daysBetween;

    }
}
