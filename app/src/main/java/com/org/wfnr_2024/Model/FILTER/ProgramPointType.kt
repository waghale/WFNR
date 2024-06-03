package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName

data class ProgramPointType(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val name: String,
    val event: String
)


