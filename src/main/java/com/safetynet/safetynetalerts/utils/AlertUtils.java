package com.safetynet.safetynetalerts.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AlertUtils {

    public static int calculateAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formatter = formatter.withLocale( Locale.FRANCE);
        LocalDate date = LocalDate.parse(birthDate, formatter);
        return Period.between(date, LocalDate.now()).getYears();
    }
}
