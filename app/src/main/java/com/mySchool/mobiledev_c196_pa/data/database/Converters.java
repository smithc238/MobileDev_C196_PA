package com.mySchool.mobiledev_c196_pa.data.database;

import androidx.room.TypeConverter;

import com.mySchool.mobiledev_c196_pa.data.entities.Status;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converter Class for Room
 * https://developer.android.com/training/data-storage/room/referencing-data#understand-no-object-references
 */
public class Converters {
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @TypeConverter
    public static String zonedDateTimeToString(ZonedDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(ISO);
    }

    @TypeConverter
    public static ZonedDateTime stringToZonedDateTime(String text) {
        return text == null ? null : ZonedDateTime.parse(text,ISO);
    }

    @TypeConverter
    public int fromStatus(Status status) {return status.getNum();}

    @TypeConverter
    public Status toStatus(int num) {return Status.values()[num];}
}
