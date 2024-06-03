package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date


import java.util.Locale

class DateConverter {

    private val iso8601Format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return if (dateString.isNullOrEmpty()) {
            null
        } else {
            // Try parsing as ISO 8601 format
            try {
                iso8601Format.parse(dateString)
            } catch (e: Exception) {
                // Fallback to parsing as default date format
                dateFormat.parse(dateString)
            }
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { iso8601Format.format(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}


