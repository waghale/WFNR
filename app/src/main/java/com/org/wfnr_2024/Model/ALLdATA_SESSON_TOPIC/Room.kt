package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val event: String,
    val id_1: String,
    val name: String,
    val roomAddition: String?
)