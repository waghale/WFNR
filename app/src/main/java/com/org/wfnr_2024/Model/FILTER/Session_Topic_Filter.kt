package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SessionTopicFilter(
    @SerializedName("@context") val context: String?,
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val addresses: List<Address>?,
    val authorEngagements: List<Any>?,
    val chairEngagements: List<Any>?,
    val contactPermission: ContactPermission?,
    val curriculumVitae: Any?,
    val event: String?,
    val firstName: String?,
    val gender: Any?,
    @SerializedName("id") val personId: String?,
    val imageFilename: Any?,
    val imageUrl: Any?,
    val isKeynoteSpeaker: Boolean?,
    val lastName: String?,
    val personAddition: Any?,
    val speakerEngagements: List<SpeakerEngagement>?,
    val thumbnailUrl: Any?,
    val title: String?
)
