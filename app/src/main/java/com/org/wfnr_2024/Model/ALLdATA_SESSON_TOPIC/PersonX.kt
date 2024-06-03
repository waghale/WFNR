package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class PersonX(
    @SerializedName("@id") val id_1: String?,
    @SerializedName("@type") val type: String?,
    val event: String,
    val firstName: String,
    val gender: Any,
    val id: String,
    val imageFilename: Any,
    val imageUrl: Any,
    val isKeynoteSpeaker: Boolean,
    val lastName: String,
    val personAddition: Any,
    val thumbnailUrl: Any,
    val title: String
)