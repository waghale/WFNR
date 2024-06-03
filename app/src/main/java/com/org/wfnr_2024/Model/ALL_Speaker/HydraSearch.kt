package com.org.wfnr_2024.Model.ALL_Speaker

import com.google.gson.annotations.SerializedName

data class HydraSearch(
    @SerializedName("@type") val type: String?,

    @SerializedName("hydra:mapping") val mapping: List<HydraMapping>,
    @SerializedName("hydra:template") val template: String?,
    @SerializedName("hydra:variableRepresentation") val variableRepresentation: String?
)