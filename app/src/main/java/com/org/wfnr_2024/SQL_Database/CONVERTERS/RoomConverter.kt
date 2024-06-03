package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Room

class RoomConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRoom(room: Room?): String? {
        return gson.toJson(room)
    }

    @TypeConverter
    fun toRoom(roomJson: String?): Room? {
        return gson.fromJson(roomJson, Room::class.java)
    }
}
