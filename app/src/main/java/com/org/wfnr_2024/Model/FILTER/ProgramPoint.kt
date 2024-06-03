package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProgramPoint(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val primaryTitle: String,
    val secondaryTitle: String?,
    val description: String?,
    val begin: String,
    val end: String,
    val languages: List<Any>,
    val hpId: Any,
    val topics: List<Any>,
    val session: Session,
    val type_1: ProgramPointType,
    val date: String,
    val talkTime: Int,
    val discussionTime: Int,
    val position: Int,
    val isEposter: Boolean
)
