package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter
import java.util.*

class date3Converters {
    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toDate(dateString: String?): Long? {
        return dateString?.let { Date.parse(it) }
    }
}
