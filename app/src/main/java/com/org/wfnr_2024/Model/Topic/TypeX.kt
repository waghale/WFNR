package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class TypeX(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val id_1: String,
    val name: String
)