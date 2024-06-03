package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter


class SubtitleConverter {

   @TypeConverter
   fun fromStringToObject(subtitle: String?): String? {
       return subtitle // Simply return the nullable string
   }

    @TypeConverter
    fun fromObjectToString(subtitle: String?): String? {
        return subtitle // Simply return the nullable string
    }

   /* @TypeConverter
    fun fromString(subtitle: String?): String? {
        return subtitle // Simply return the nullable string
    }

    @TypeConverter
    fun toString(subtitle: String?): String? {
        return subtitle // Simply return the nullable string
    }*/
}

