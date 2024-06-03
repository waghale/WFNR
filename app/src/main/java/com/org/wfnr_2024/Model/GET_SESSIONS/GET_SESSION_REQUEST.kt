package com.org.wfnr_2024.Model.GET_SESSIONS

data class GET_SESSION_REQUEST(
    val event:String?=null,
    val date:String?=null,
    val groups:List<String>?=null,
    val pagination:Boolean?=false


)