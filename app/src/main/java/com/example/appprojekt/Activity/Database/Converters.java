package com.example.appprojekt.Activity.Database;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
