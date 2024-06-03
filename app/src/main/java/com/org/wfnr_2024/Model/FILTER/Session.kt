package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Session(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val title: String,
    val subtitle: String?,
    val begin: String,
    val end: String,
    val date: String,
    val languages: List<Any>,
    val description: String?,
    val type_1: SessionType,
    val room: Room,
    val topics: List<Any>,
    val programAreas: List<Any>,
    val sessionAddition: Any
)
