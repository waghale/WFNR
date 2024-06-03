package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val addresses: List<Addresse>,
    val event: String,
    val firstName: String,
    val gender: Any,
    val id_1: String,
    val imageFilename: Any,
    val imageUrl: Any,
    val isKeynoteSpeaker: Boolean,
    val lastName: String,
    val personAddition: Any,
    val thumbnailUrl: Any,
    val title: String
)