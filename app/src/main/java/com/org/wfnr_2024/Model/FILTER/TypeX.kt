package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypeX(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val event: String,
    val id_1: String,
    val name: String
):Serializable