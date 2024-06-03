package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Room(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val name: String,
    val event: String,
    val roomAddition: Any
)
