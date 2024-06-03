package com.org.wfnr_2024.Model.SessionModel

import com.google.gson.annotations.SerializedName

data class HydraMapping(
    @SerializedName("@type")val type: String,
    val `property`: String,
    val required: Boolean,
    val variable: String
)