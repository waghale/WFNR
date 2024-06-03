package com.org.wfnr_2024.Model.Chair_Speaker_List

data class ChairSpeakerList(
    val id:String?=null,
    val type:String?=null,
    val title:String?=null,
    val date:String?=null,
    val start_time:String?=null,
    val end_time:String?=null,
    val person_name:String?=null,
    val room_name:String?=null,
    var timeslotTitle:String?=null,
    var sessio_topic_title:String?=null

)
