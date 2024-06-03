package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE

class ChairListConverter {
    @TypeConverter
    fun fromString(value: String): List<GET_SESSION_RESPONSE.HydraMember.Chair> {
        val listType = object : TypeToken<List<GET_SESSION_RESPONSE.HydraMember.Chair>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<GET_SESSION_RESPONSE.HydraMember.Chair>): String {
        return Gson().toJson(list)
    }
}
