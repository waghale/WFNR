package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE.HydraMember.Topic


class TopicsConverter {
    @TypeConverter
    fun fromTopicList(value: List<Topic>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Topic>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTopicList(value: String?): List<Topic>? {
        val gson = Gson()
        val type = object : TypeToken<List<Topic>>() {}.type
        return gson.fromJson(value, type)
    }
}


