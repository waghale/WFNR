package com.org.wfnr_2024.Model.GET_SESSIONS


import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class GET_SESSION_RESPONSE(
    @SerializedName("@context")val context: String,
    @SerializedName("@id")val id: String,
    @SerializedName("@type")val type: String,
    @SerializedName("hydra:member")val member: List<HydraMember>,
    @SerializedName("hydra:search")val search: HydraSearch,
    @SerializedName("hydra:totalItems")val totalItems: Int,
    @SerializedName("hydra:view")val view: HydraView
) {
    data class HydraMember(
        @SerializedName("@id") val id: String,
        @SerializedName("@type")val type: String,
        val begin: String,
        val chairs: List<Chair>,
        val date: String,
        val description: String,
        val end: String,
        @SerializedName("id") val id1: String,
        val isEposterSession: Boolean,
        val languages: List<String>,
        val programAreas: List<ProgramArea>,
        val programPoints: List<ProgramPoint>,
        val room: Room,
        val roomOrderPosition: Int,
        val sessionAddition:SessionAddition,
        val sessionGroup: String,
        val subtitle: String,
        val timeslotTitle: String,
        val title: String,
        val topics: List<Topic>,
        @SerializedName("type")val type1: Type
    ) {
        data class Chair(
            @SerializedName("@type")val type: String,
            val id: Int,
            val person: Person,
            val position: Int,
            @SerializedName("type")val type1: Type
        ) {
            data class Person(
                @SerializedName("@id") val id: String,
                @SerializedName("@type")val type: String,
                val addresses: List<Addresse>,
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
            }

            data class Type(
                @SerializedName("@id")val id: String,
                @SerializedName("@type")val type: String,
                @SerializedName("id")val id1: String,
                val name: String,
                val position: Int
            )
        }

        data class ProgramArea(
            @SerializedName("@id")val id: String,
            @SerializedName("@type")val type: String,
            @SerializedName("id")val id1: String,
            val name: String,
            val slug: String
        )

        data class ProgramPoint(
            @SerializedName("@id")val id: String,
            @SerializedName("@type")val type: String,
            val begin: String,
            val date: String,
            val description: String,
            val discussionTime: Int,
            val end: String,
            val hpId: Any,
            @SerializedName("id")val id1: String,
            val isEposter: Boolean,
            val languages: List<Any>,
            val paper: Any,
            val position: Int,
            val poster: Any,
            val primaryTitle: String,
            val secondaryTitle: Any,
            val speakers: List<Speaker>,
            val talkTime: Int,
            val topics: List<Any>,
            @SerializedName("type")val type1: Type
        ): Serializable
        {
            data class Speaker(
                @SerializedName("@type")val type: String,
                val person: Person,
                val position: Int,
                @SerializedName("type")val type1: Type
            ):Serializable {

                data class Person(
                    @SerializedName("@id") val id: String,
                    @SerializedName("@type")val type: String,
                    val addresses: List<Addresse>,
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
                ):Serializable {
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
                    ):Serializable
                }

                data class Type(
                    @SerializedName("@id")val id: String,
                    @SerializedName("@type")val type: String,
                    @SerializedName("id")val id1: String,
                    val name: String,
                    val position: Int
                )
            }

            data class Type(
                @SerializedName("@id")val id: String,
                @SerializedName("@type")val type: String,
                @SerializedName("id")val id1: String,
                val name: String
            )



        }

        data class Topic(
            @SerializedName("@id")val id: String,
            @SerializedName("@type")val type: String,
            val color: String,
            @SerializedName("id")val id1: String,
            val name: String,
            val parentTopicId: Any,
            val programAreas: List<Any>
        ):Serializable

        data class Room(
            @SerializedName("@id")val id: String,
            @SerializedName("@type")val type: String,
            val event: String,
            @SerializedName("id")val id1: String,
            val name: String,
            val roomAddition: Any
        )
        data class SessionAddition(
            @SerializedName("@type") val type: String,
            val accessRestricted: String,
            val chargeable: Any,
            val isHighlight: Boolean,
            val itemContentText: Any,
            val listContentText: Any,
            val logo: Any,
            val maximumParticipants: Any,
            val redirectUrl: Any
        )

        data class Type(
            @SerializedName("@id")val id: String,
            @SerializedName("@type")val type: String,
            val color: String,
            val colorBackground: String,
            val colorBorder: String,
            val colorFont: String,
            val colorHighlight: String,
            val description: String,
            @SerializedName("id")val id1: String,
            val name: String
        )
    }

    data class HydraSearch(
        @SerializedName("@type")val type: String,
        @SerializedName("hydra:mapping")val mapping: List<HydraMapping>,
        @SerializedName("hydra:template")val template: String,
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
        @SerializedName("@id")val id: String,
        @SerializedName("@type")val type: String
    )


}