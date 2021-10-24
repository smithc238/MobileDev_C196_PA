package com.mySchool.mobiledev_c196_pa.utilities;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConv {
    private static final DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ofPattern("dd MMM yy");

    public static String dateToStringLocal(ZonedDateTime dateTime) {
        return dateTime.toLocalDateTime().format(LOCAL_DATE);
    }

    public static ZonedDateTime stringToDateLocal(String text) {
        return ZonedDateTime.parse(text,LOCAL_DATE);
    }
}
