package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.TypeConverter
import com.google.gson.Gson


import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE.HydraMember.Type

class SessionAdditionConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Type? {
        return if (value == null) {
            null
        } else {
            // Convert the JSON string to the appropriate type (Type) based on your data model
            gson.fromJson(value, Type::class.java)
        }
    }

    @TypeConverter
    fun toString(sessionAddition: Type?): String? {
        return if (sessionAddition == null) {
            null
        } else {
            // Convert the object (Type) to a JSON string
            gson.toJson(sessionAddition)
        }
    }
}

