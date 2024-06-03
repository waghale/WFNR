package com.org.wfnr_2024.SQL_Database.CONVERTERS


import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomAdditionTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromRoomAddition(roomAddition: Any?): String? {
        return roomAddition?.let {
            gson.toJson(it)
        }
    }

    @TypeConverter
    fun toRoomAddition(roomAdditionString: String?): Any? {
        return roomAdditionString?.let {
            gson.fromJson(it, Any::class.java) // Change Any::class.java to the expected type
        }
    }
}
