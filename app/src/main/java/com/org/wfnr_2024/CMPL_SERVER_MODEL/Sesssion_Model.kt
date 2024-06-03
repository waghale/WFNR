package com.org.wfnr_2024.CMPL_SERVER_MODEL



data class Sesssion_Model(
    val sessions: List<Session>
) {
    data class Session(
        val begin: String?=null,
        val chair: String?=null,
        val chairs: List<Chair>,
        val created_at: String?=null,
        val date: String?=null,
        val description: String?=null,
        val end: String?=null,
        val id: String?=null,
        val isEposterSession: Int,
        val program_points: List<ProgramPoint>,
        val room: Room,
        val roomOrderPosition: Int,
        val room_id: String?=null,
        val subtitle: String?=null,
        val timeslotTitle: String?=null,
        val title: String?=null,
        val type: Type,
        val type_id: String?=null,
        val updated_at: String?=null
    ) {
        data class Chair(
            val created_at: String?=null,
            val id: Int,
            val person: Person,
            val person_id: String?=null,
            val position: Int,
            val session_id: String?=null,
            val updated_at: String?=null
        ) {
            data class Person(
                val city: String?=null,
                val company: String?=null,
                val country: String?=null,
                val created_at: String?=null,
                val department: String?=null,
                val displayValue: String?=null,
                val firstName: String?=null,
                val gender: String?=null,
                val id: String?=null,
                val lastName: String?=null,
                val state: String?=null,
                val street: String?=null,
                val title: String?=null,
                val updated_at: String?=null,
                val zip: String?=null
            )
        }

        data class ProgramPoint(
            val begin: String?=null,
            val created_at: String?=null,
            val discussionTime: Int,
            val end: String?=null,
            val id: String?=null,
            val isEposter: Int,
            val position: Int,
            val primaryTitle: String?=null,
            val secondaryTitle: String?=null,
            val session_id: String?=null,
            val speaker: String?=null,
            val speakers: List<Speaker>,
            val talkTime: Int,
            val updated_at: String?=null
        ) {
            data class Speaker(
                val created_at: String?=null,
                val id: Int,
                val person: Person,
                val person_id: String?=null,
                val position: String?=null,
                val program_point_id: String?=null,
                val updated_at: String?=null
            ) {
                data class Person(
                    val city: String?=null,
                    val company: String?=null,
                    val country: String?=null,
                    val created_at: String?=null,
                    val department: String?=null,
                    val displayValue: String?=null,
                    val firstName: String?=null,
                    val gender: String?=null,
                    val id: String?=null,
                    val lastName: String?=null,
                    val state: String?=null,
                    val street: String?=null,
                    val title: String?=null,
                    val updated_at: String?=null,
                    val zip: String?=null
                )
            }
        }

        data class Room(
            val created_at: String?=null,
            val event: String?=null,
            val id: String?=null,
            val name: String?=null,
            val roomAddition: String?=null,
            val updated_at: String?=null
        )

        data class Type(
            val color: String?=null,
            val colorBackground: String?=null,
            val colorBorder: String?=null,
            val colorFont: String?=null,
            val colorHighlight: String?=null,
            val created_at: String?=null,
            val description: String?=null,
            val id: String?=null,
            val name: String?=null,
            val updated_at: String?=null
        )
    }
}