package com.org.wfnr_2024.Model.GET_QUESTIONS

data class get_Question_member_wise(
    val `data`: List<Data>,
    val message: String,
    val status_code: Int
) {
    data class Data(
        val created_at: String,
        val hall: String,
        val id: Int,
        val is_active: Int,
        val is_answered: Int,
        val member_id: String,
        val question: String,
        val question_forward: Int,
        val session_id: String,
        val topic_id: String,
        val updated_at: String
    )
}