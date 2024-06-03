package com.org.wfnr_2024.SQL_Database.ENTITY.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val roomId: String,
    val name: String,
    val event: String,
    val type: String?, // New property for room type
    val id_1: String,

    val roomAddition: String?
    // Add other properties as needed
)
