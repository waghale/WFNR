package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE

class RoomConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRoom(room: GET_SESSION_RESPONSE.HydraMember.Room): String {
        return gson.toJson(room)
    }

    @TypeConverter
    fun toRoom(roomJson: String): GET_SESSION_RESPONSE.HydraMember.Room {
        val type = object : TypeToken<GET_SESSION_RESPONSE.HydraMember.Room>() {}.type
        return gson.fromJson(roomJson, type)
    }
}
