package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "Topic_Itinerary")
data class Topic_Itinerary(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val date: String,
    val begin:String,
    val end:String,
    val topic_name:String,
    val speaker_name:String,
    val topic_itinerary:String,
    val wcnr_id:String,//member_id
    val wcnr_name:String,
    val wcnr_email:String,
    val inserted_date:String,
    val topic_id:String,
    val is_fav:String,
    val room_name:String,
    val begin_formated:String,
    val end_formated:String,
    val begin_date_formated:String,
    val begin_time_formated:String,
    val end_date_formated:String,
    val end_time_formated:String
)

