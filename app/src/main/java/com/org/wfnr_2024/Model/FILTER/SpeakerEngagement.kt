package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpeakerEngagement(
    @SerializedName("@type") val type: String,
    @SerializedName("@id") val id: String,
    val position: Int,
    val programPoint: ProgramPoint
)
