package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName
import com.org.wfnr_2024.Model.SessionModel.HydraMapping

data class HydraSearch(
    @SerializedName("@type") val type: String?,

    @SerializedName("hydra:mapping") val mapping: List<HydraMapping>,
    @SerializedName("hydra:template") val template: String?,
    @SerializedName("hydra:variableRepresentation") val variableRepresentation: String?
)