package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Type(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val color: String,
    val colorBackground: String,
    val colorBorder: String,
    val colorFont: String,
    val colorHighlight: String,
    val description: String,
    val id_1: String,
    val name: String
):Serializable