package com.org.wfnr_2024.Model.Topic_Question_Server

data class Topic_Question_Request(
    val member_id:String?=null,
    val topic_id:String?=null,
    val session_id:String?=null,
    val question:String?=null,
    val hall:String?=null,
)
