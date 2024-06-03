package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomSessionConverter {
    @TypeConverter
    fun fromRoom(room: Session.Room): String {
        return Gson().toJson(room)
    }

    @TypeConverter
    fun toRoom(roomString: String): Session.Room {
        return Gson().fromJson(roomString, Session.Room::class.java)
    }
}
