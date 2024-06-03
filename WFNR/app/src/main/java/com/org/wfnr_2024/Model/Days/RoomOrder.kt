package com.org.wfnr_2024.Model.Days

import com.google.gson.annotations.SerializedName

data class RoomOrder(
    @SerializedName("@type") val type: String?,
    val date: String,
    val dateString: String,
    val event: String,
    val name: String,
    val position: Int,
    val roomAddition: Any,
    val roomId: String
)