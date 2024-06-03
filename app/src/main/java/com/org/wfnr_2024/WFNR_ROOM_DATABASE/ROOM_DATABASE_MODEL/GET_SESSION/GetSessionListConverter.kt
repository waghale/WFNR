package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GetSessionListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<Get_Session>? {
        val listType = object : TypeToken<List<Get_Session>>() {}.type
        return gson.fromJson<List<Get_Session>>(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<Get_Session>?): String {
        return gson.toJson(list)
    }
}
