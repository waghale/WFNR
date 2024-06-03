package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class Speaker(
    @SerializedName("@type") val type: String,
    val person: PersonX,
    val position: Int,
    val type_1: Type
)