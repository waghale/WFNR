package com.org.wfnr_2024.SQL_Database



import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.SessionModel.HydraMember

class Converter_session {

    private val gson = Gson()

    @TypeConverter
    fun fromHydraMemberList(hydraMemberList: List<HydraMember>): String {
        return gson.toJson(hydraMemberList)
    }

    @TypeConverter
    fun toHydraMemberList(hydraMemberListString: String): List<HydraMember> {
        val type = object : TypeToken<List<HydraMember>>() {}.type
        return gson.fromJson(hydraMemberListString, type)
    }
}
