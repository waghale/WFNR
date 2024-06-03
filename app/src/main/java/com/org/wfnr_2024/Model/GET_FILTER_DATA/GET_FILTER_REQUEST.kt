package com.org.wfnr_2024.Model.GET_FILTER_DATA

data class GET_FILTER_REQUEST(
    val hasChairOrSpeakerEngagements:Boolean?=true,
    val pagination:Boolean?=false,
    val event:String?=null
)
