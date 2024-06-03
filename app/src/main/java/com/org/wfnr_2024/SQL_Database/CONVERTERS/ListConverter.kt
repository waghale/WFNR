package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Chair
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.ProgramPoint

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun chairListToString(chairs: List<Chair>?): String? {
        return gson.toJson(chairs)
    }

    @TypeConverter
    fun stringToChairList(chairString: String?): List<Chair>? {
        val type = object : TypeToken<List<Chair>>() {}.type
        return gson.fromJson<List<Chair>>(chairString, type)
    }

    @TypeConverter
    fun anyListToString(anyList: List<Any>?): String? {
        return gson.toJson(anyList)
    }

    @TypeConverter
    fun stringToAnyList(anyString: String?): List<Any>? {
        val type = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson<List<Any>>(anyString, type)
    }

    @TypeConverter
    fun anyToString(any: Any?): String? {
        return gson.toJson(any)
    }

    @TypeConverter
    fun stringToAny(anyString: String?): Any? {
        val type = object : TypeToken<Any>() {}.type
        return gson.fromJson<Any>(anyString, type)
    }
}
