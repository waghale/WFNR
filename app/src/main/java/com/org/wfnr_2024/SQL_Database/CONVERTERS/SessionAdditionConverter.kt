package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.TypeXXX


class SessionAdditionConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromSessionAddition(sessionAdditionString: String?): TypeXXX {
        return gson.fromJson(sessionAdditionString, TypeXXX::class.java)
    }

    @TypeConverter
    fun toSessionAddition(typeXXX: TypeXXX?): String {
        return gson.toJson(typeXXX)
    }
}
