package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContactPermission(
    @SerializedName("@type") val type: String,
    val consent: Boolean,
    val answered: Boolean,
    val event: String
)
