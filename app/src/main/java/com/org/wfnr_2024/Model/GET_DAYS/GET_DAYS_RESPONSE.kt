package com.org.wfnr_2024.Model.GET_DAYS

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GET_DAYS_RESPONSE(
    @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    @SerializedName("hydra:member") val member: List<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView
) {
    data class HydraMember(
        @SerializedName("@id") val id: String,
        @SerializedName("@type") val type: String,
        val date: String,
        val id1: Int,
        val programAreas: List<Any>,
        val roomOrders: List<RoomOrder>
    ) {
        data class RoomOrder(
            @SerializedName("@type") val type: String,
            val date: String,
            val dateString: String,
            val event: String,
            val name: String,
            val position: Int,
            val roomAddition: Any,
            val roomId: String
        )
    }

    data class HydraSearch(
        @SerializedName("@type") val type: String,
        @SerializedName("hydra:mapping") val mapping: List<HydraMapping>,
        @SerializedName("hydra:template")  val template: String,
        @SerializedName("hydra:variableRepresentation") val variableRepresentation: String
    ) {
        data class HydraMapping(
            @SerializedName("@type") val type: String,
            val `property`: String,
            val required: Boolean,
            val variable: String
        )
    }

    data class HydraView(
        @SerializedName("@id") val id: String,
        @SerializedName("@type") val type: String
    )
}