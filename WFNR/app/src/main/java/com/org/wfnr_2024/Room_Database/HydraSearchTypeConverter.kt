package com.org.wfnr_2024.Room_Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.org.wfnr_2024.Model.SessionModel.HydraSearch

class HydraSearchTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): HydraSearch {
        return gson.fromJson(json, HydraSearch::class.java)
    }

    @TypeConverter
    fun toJson(search: HydraSearch): String {
        return gson.toJson(search)
    }
}
