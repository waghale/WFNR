package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class TypeXXX(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val color: String,
    val colorBackground: String,
    val colorBorder: String,
    val colorFont: String,
    val colorHighlight: String,
    val description: String,
    val id_1: String,
    val name: String
)