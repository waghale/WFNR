package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val id_1: String,
    val name: String,
    val position: Int
)