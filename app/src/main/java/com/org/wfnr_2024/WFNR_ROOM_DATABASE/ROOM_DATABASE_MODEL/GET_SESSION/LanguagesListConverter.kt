package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LanguagesListConverter {
    @TypeConverter
    fun fromString(value: String): List<Object> {
        val listType = object : TypeToken<List<Object>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Object>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
