package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE.HydraMember.ProgramPoint

class ProgramPointListConverter {
    @TypeConverter
    fun fromString(value: String): List<ProgramPoint> {
        val listType = object : TypeToken<List<ProgramPoint>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ProgramPoint>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
