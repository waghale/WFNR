package com.org.wfnr_2024.Model.FEEDBACK

data class Send_FeedBack_To_Server_Request(
    val member_id:String?=null,
    val relevance:String?=null,
    val quality:String?=null,
    val speaker_profile:String?=null,
    val venue:String?=null,
    val arrangements:String?=null,
    val comments:String?=null,
    val is_active:Int?=0,



)
