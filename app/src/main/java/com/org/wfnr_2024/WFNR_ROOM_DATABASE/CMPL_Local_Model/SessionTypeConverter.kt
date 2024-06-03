package com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model

import androidx.room.TypeConverter

class SessionTypeConverter {
    @TypeConverter
    fun fromString(value: String?): Session.Type? {
        // Implement conversion from String to Session.Type
        return value?.let {
            // Parse the string and return a Session.Type object
            // Example:
            // val typeParts = value.split(",")
            // return Session.Type(typeParts[0], typeParts[1], ...)
            null // Return the parsed Session.Type object
        }
    }

    @TypeConverter
    fun toString(type: Session.Type?): String? {
        // Implement conversion from Session.Type to String
        return type?.let {
            // Convert the Session.Type object to a string representation
            // Example:
            // return "${type.color},${type.description},..."
            null // Return the string representation
        }
    }
}


