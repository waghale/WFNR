package com.org.wfnr_2024.WFNR_ROOM_DATABASE

import androidx.room.TypeConverter
import java.util.Date

class DateConverter1 {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
