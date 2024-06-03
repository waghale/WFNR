package com.org.wfnr_2024.Model.ALL_Speaker

import com.google.gson.annotations.SerializedName


data class Spesker_list(
    @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    @SerializedName("hydra:member") val member: ArrayList<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView
)