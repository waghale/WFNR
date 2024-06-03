package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class ProgramPoint(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val begin: String,
    val date: String,
    val description: String,
    val discussionTime: Int,
    val end: String,
    val hpId: Any,
    val id_1: String,
    val isEposter: Boolean,
    val languages: List<Any>,
    val paper: Any,
    val position: Int,
    val poster: Any,
    val primaryTitle: String,
    val secondaryTitle: Any,
    val speakers: List<Speaker>,
    val talkTime: Int,
    val topics: List<Any>,
    val type_1: TypeXX
)