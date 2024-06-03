package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.ProgramPoint

class ProgramPointListConverter {

    @TypeConverter
    fun fromProgramPointsList(programPoints: List<ProgramPoint>?): String? {
        if (programPoints == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(programPoints)
    }

    @TypeConverter
    fun toProgramPointsList(programPointsString: String?): List<ProgramPoint>? {
        if (programPointsString == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<ProgramPoint>>() {}.type
        return gson.fromJson<List<ProgramPoint>>(programPointsString, listType)
    }
}
