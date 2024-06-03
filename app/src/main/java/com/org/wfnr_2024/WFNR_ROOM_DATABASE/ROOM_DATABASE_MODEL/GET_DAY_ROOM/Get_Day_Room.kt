package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE


@Entity(tableName = "Get_Day_Room")
data class Get_Day_Room(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val date:String?=null,
    val roomList: List<GET_DAYS_RESPONSE.HydraMember.RoomOrder>
)

