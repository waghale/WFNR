package com.org.wfnr_2024.Model.FEEDBACK

data class FeedBack_Request(
    val member_id:String?=null,
    val topic_id:String?=null,
    val rating:String?=null,
    val comments:String?=null,
)
