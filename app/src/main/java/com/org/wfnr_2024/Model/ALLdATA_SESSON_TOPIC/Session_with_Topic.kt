package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import com.google.gson.annotations.SerializedName

data class Session_with_Topic(
    @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    @SerializedName("hydra:member") val member: ArrayList<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView

)