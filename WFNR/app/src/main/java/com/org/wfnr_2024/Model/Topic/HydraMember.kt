package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class HydraMember(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val begin: String,
    val date: String,
    val description: Any,
    val discussionTime: Int,
    val end: String,
    val hpId: Any,
    @SerializedName("id") val id1: String,
    val isEposter: Boolean,
    val languages: List<Any>,
    val position: Int,
    val poster: Any,
    val primaryTitle: String,
    val secondaryTitle: Any,
    val speakers: List<Speaker>,
    val talkTime: Int,
    val topics: List<Any>,
    @SerializedName("type") val type1: Type
)