package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ChairConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<Session.Chair> {
        val listType = object : TypeToken<List<Session.Chair>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(chairs: List<Session.Chair>): String {
        return gson.toJson(chairs)
    }
}