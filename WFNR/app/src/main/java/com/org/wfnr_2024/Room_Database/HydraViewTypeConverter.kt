package com.org.wfnr_2024.Room_Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.org.wfnr_2024.Model.SessionModel.HydraView

class HydraViewTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): HydraView {
        return gson.fromJson(json, HydraView::class.java)
    }

    @TypeConverter
    fun toJson(view: HydraView): String {
        return gson.toJson(view)
    }
}
