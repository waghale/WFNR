package com.org.wfnr_2024.SQL_Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.Days.HydraMember

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<HydraMember> {
        val listType = object : TypeToken<List<HydraMember>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<HydraMember>): String {
        return gson.toJson(list)
    }
}
