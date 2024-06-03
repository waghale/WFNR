package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Chair(
    @SerializedName("type") val type: Any, // Change this to the appropriate type based on JSON
    val id: Int,
    val person: Person,
    val position: Int,
    val type_1: Type
):Serializable