package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProgramPointsConverter {

    @TypeConverter
    fun fromList(programPoints: List<Session.ProgramPoint>): String {
        return Gson().toJson(programPoints)
    }

    @TypeConverter
    fun toList(programPointsString: String): List<Session.ProgramPoint> {
        val type = object : TypeToken<List<Session.ProgramPoint>>() {}.type
        return Gson().fromJson(programPointsString, type)
    }
}
