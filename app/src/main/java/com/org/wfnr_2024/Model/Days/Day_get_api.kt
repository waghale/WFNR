package com.org.wfnr_2024.Model.Days

import com.google.gson.annotations.SerializedName

data class Day_get_api(
    @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    @SerializedName("hydra:member") val member: ArrayList<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView
)

