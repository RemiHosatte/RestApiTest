package com.apitest.demo.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {

    public static boolean isIsoDate(String date) {
        try {
            Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
            return true;
        } catch (DateTimeParseException ignore) {

        }
        return false;
    }

    public static boolean isAnAdult(String date) {
        try {
            Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
            LocalDateTime dateParse = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

            Period period = Period.between(dateParse.toLocalDate(), LocalDate.now());
            return period.getYears() >= 18;
        } catch (DateTimeParseException e) {
            //log the failure here
            e.printStackTrace();
        }
        return false;
    }
}
