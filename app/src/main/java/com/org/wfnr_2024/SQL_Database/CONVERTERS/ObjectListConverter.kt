package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ObjectListConverter {

    @TypeConverter
    fun fromObjectList(objects: List<Any>?): String? {
        if (objects == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(objects)
    }

    @TypeConverter
    fun toObjectList(objectString: String?): List<Any>? {
        if (objectString == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson<List<Any>>(objectString, listType)
    }
}
