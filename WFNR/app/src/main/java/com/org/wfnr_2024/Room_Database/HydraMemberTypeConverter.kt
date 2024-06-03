package com.org.wfnr_2024.Room_Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.org.wfnr_2024.Model.SessionModel.HydraMember

class HydraMemberTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<HydraMember> {
        val type = object : TypeToken<List<HydraMember>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun toJson(memberList: List<HydraMember>): String {
        return gson.toJson(memberList)
    }
}
