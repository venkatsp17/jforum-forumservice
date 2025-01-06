package com.example.forumservice.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // Get current date
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    // Get current time
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    // Get current date and time
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static String formatDateTimeWithPattern(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }
}