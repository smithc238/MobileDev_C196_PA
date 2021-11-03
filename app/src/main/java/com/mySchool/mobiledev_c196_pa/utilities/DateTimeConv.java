package com.mySchool.mobiledev_c196_pa.utilities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeConv {
    private static final DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ofPattern("dd-MMM-yy");

    public static String dateToStringLocal(ZonedDateTime dateTime) {
        return dateTime.toLocalDateTime().format(LOCAL_DATE);
    }

    public static ZonedDateTime stringToDateLocalWithoutTime(String text) {
        return LocalDate.parse(text,LOCAL_DATE).atTime(8,0,0,0).atZone(ZoneId.systemDefault());
    }
}
