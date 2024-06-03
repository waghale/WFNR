package com.org.wfnr_2024.SQL_Database.ENTITY.Session

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Room

@Entity(tableName = "sessions")
data class SessionDataEntity(
    @PrimaryKey val id: String,
    val roomId: String, // Foreign key to relate session with room
    val name: String,
    val begin: String,
    val end: String,
    val date: String,
    val title: String,
    val roomOrderPosition: Int,
    val room: Room?,
    val description: String?,
    val timeslotTitle: String?,

    )
