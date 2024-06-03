package com.org.wfnr_2024.Model.GET_NOTES

data class Get_Notes_Response(
    val `data`: List<Data>,
    val message: String,
    val status_code: Int
) {
    data class Data(
        val create_date: String,
        val end_time: String,
        val id: Int,
        val is_active: Int,
        val mobileno: Any,
        val moderator: Any,
        val notes: String,
        val panellist: Any,
        val public_is_active: Any,
        val question_active: Int,
        val session_id: String,
        val speaker: String,
        val start_date: String,
        val start_time: String,
        val topic: String,
        val topic_id: String,
        val uploaded_video: Any
    )
}