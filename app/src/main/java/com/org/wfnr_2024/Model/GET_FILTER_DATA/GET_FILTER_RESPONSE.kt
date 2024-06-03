package com.org.wfnr_2024.Model.GET_FILTER_DATA

import com.google.gson.annotations.SerializedName

data class GET_FILTER_RESPONSE(
    @SerializedName("@context")val context: String,
    @SerializedName("@id")val id: String,
    @SerializedName("@type")val type: String,
    @SerializedName("hydra:member")val member: List<HydraMember>,
    @SerializedName("hydra:search")val search: HydraSearch,
    @SerializedName("hydra:totalItems")val totalItems: Int,
    @SerializedName("hydra:view")val view: HydraView
) {
    data class HydraMember(
        @SerializedName("@id")val id: String,
        @SerializedName("@type")val type: String,
        val addresses: List<Addresse>,
        val contactPermission: ContactPermission,
        val event: String,
        val firstName: String,
        val gender: Any,
        @SerializedName("id")val id1: String,
        val imageFilename: Any,
        val imageUrl: Any,
        val isKeynoteSpeaker: Boolean,
        val lastName: String,
        val personAddition: Any,
        val thumbnailUrl: Any,
        val title: String
    ) {
        data class Addresse(
            @SerializedName("@type")val type: String,
            val city: String,
            val company: String,
            val country: String,
            val department: Any,
            val displayValue: String,
            val state: Any,
            val street: String,
            val zip: String
        )

        data class ContactPermission(
            @SerializedName("@id")val id: String,
            val answered: Boolean,
            val consent: Boolean,
            val event: String
        )
    }

    data class HydraSearch(
        @SerializedName("@type")val type: String,
        @SerializedName("hydra:mapping")val mapping: List<HydraMapping>,
        @SerializedName("hydra:template")val template: String,
        @SerializedName("hydra:variableRepresentation")val variableRepresentation: String
    ) {
        data class HydraMapping(
            @SerializedName("@type")val type: String,
            val `property`: String,
            val required: Boolean,
            val variable: String
        )
    }

    data class HydraView(
        @SerializedName("@id")val id: String,
        @SerializedName("@type")val type: String
    )
}