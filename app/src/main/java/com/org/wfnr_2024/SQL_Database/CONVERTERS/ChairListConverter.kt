package com.org.wfnr_2024.SQL_Database.CONVERTERS

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Chair

class ChairListConverter {

    @TypeConverter
    fun fromChairList(chairs: List<Chair>?): String? {
        if (chairs == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(chairs)
    }

    @TypeConverter
    fun toChairList(chairString: String?): List<Chair>? {
        if (chairString == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Chair>>() {}.type
        return gson.fromJson<List<Chair>>(chairString, listType)
    }
}
