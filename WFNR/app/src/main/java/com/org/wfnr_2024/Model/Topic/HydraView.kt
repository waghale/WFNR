package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class HydraView(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?
)