package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class Speaker(
    @SerializedName("@type") val type: String?, val person: Person,
    val position: Int,
    val type_1: Type
)