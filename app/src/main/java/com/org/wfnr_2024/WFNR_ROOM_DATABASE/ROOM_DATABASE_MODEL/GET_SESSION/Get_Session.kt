package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import java.io.Serializable

@Entity(tableName = "Get_Session")
data class Get_Session(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val begin: String,
    val date: String,
    val description: String,
    @SerializedName("id") val id1: String,
    val chairs: List<GET_SESSION_RESPONSE.HydraMember.Chair>,
    val end: String,
    val programPoints: List<GET_SESSION_RESPONSE.HydraMember.ProgramPoint>,
    val room: GET_SESSION_RESPONSE.HydraMember.Room,
    val timeslotTitle: String? = null,
    val title: String? = null,
    val topics: List<GET_SESSION_RESPONSE.HydraMember.Topic>? = null,
) : Serializable {

}


