package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Topic_FeedBack")
data class Topic_FeedBack(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val date: String,
    val begin:String,
    val end:String,
    val topic_name:String,
    val speaker_name:String,
    val topic_rating:String,
    val wcnr_id:String,//server_member_id
    val wcnr_name:String,
    val wcnr_email:String,
    val inserted_date:String,
    val topic_id:String
)
