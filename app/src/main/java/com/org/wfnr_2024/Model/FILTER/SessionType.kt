package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName

data class SessionType(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val name: String,
    val color: String,
    val description: String,
    val colorBackground: String,
    val colorBorder: String,
    val colorFont: String,
    val colorHighlight: String
)