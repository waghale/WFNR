package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Login_Local")
data class LOGIN_LOCAL(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val user_name:String?=null,
    val wcnr_reg_no:String?=null,
    val user_email_id:String?=null,
    val user_city:String?=null,
    val user_country:String?=null,
    val member_id:String?=null

    )
