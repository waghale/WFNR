package com.org.wfnr_2024.Model.ALL_Speaker

import java.io.Serializable

data class HydraMember(
    val id: String,
    val type: String,
    val addresses: List<Addresse>,
    val contactPermission: ContactPermission,
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
):Serializable