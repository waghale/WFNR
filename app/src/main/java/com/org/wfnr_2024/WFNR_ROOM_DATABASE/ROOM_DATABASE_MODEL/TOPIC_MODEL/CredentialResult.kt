package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL

data class CredentialResult(
    val success:Boolean,
    val member_id:String?=null,
    var email:String?=null
)
