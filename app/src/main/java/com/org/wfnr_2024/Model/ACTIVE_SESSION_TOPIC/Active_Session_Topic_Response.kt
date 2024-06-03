package com.org.wfnr_2024.Model.ACTIVE_SESSION_TOPIC

data class Active_Session_Topic_Response(
    val `data`: List<Data>,
    val message: String,
    val status_code: Int
) {
    data class Data(
        val chairman: String,
        val conference_type: String,
        val create_date: String,
        val current_session: Int,
        val end_time: String,
        val hall: String,
        val id: Int,
        val is_active: Int,
        val mobileno: Any,
        val moderator: Any,
        val modify_date: Any,
        val panellist: Any,
        val penalist: Any,
        val public_is_active: Any,
        val question_active: Int,
        val rapporteur: Any,
        val session_id: String,
        val session_name: String,
        val speaker: String,
        val start_date: String,
        val start_time: String,
        val title: String,
        val topic: String,
        val topic_id: String,
        val track: String,
        val type: Int,
        val uploaded_video: Any
    )
}