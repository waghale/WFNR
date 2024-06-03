package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class HydraMember(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val begin: String,
    val title: String,
    val chairs: List<Chair>,
    val date: String,
    val description: String,
    val end: String,
    val id_1: String,
    val isEposterSession: Boolean,
    val languages: List<Any>,
    val programAreas: List<Any>,
    val programPoints: List<ProgramPoint>,
    val room: Room,
    val roomOrderPosition: Int,
    val sessionAddition: Any,
    val sessionGroup: Any,
    val subtitle: Any,
    val timeslotTitle: String,
    val topics: List<Any>,
    val type_1: TypeXXX
)