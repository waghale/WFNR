package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE.HydraMember.RoomOrder

class RoomOrderListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<RoomOrder> {
        val listType = object : TypeToken<List<RoomOrder>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(roomOrderList: List<RoomOrder>): String {
        return gson.toJson(roomOrderList)
    }
}
