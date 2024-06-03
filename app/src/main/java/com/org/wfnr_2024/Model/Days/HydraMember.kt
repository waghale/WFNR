package com.org.wfnr_2024.Model.Days

import com.google.gson.annotations.SerializedName

data class HydraMember(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val date: String,
    @SerializedName("id") val id1: String,
    val programAreas: List<Any>,
    val roomOrders: List<RoomOrder>
)