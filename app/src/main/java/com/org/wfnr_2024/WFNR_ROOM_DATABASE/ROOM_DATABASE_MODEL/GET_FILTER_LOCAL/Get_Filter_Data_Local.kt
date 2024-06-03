package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Get_Filter_Data_Local")
data class Get_Filter_Data_Local(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val id:String?=null,
    val title:String?=null,
    val first_name:String?=null,
    val last_name:String?=null
)
