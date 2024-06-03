package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypeXX(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val id_1: String,
    val name: String,
    val position: Int
):Serializable