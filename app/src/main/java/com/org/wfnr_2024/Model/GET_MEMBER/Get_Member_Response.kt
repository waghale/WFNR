package com.org.wfnr_2024.Model.GET_MEMBER

data class Get_Member_Response(
    val `data`: List<Data>,
    val message: String,
    val status_code: Int
) {
    data class Data(
        val WFNR_reg_no: String,
        val city: String,
        val country: String,
        val created_at: String,
        val deleted_at: Any,
        val email: String,
        val fname: String,
        val id: Int,
        val is_deleted: Int,
        val isactive: Int,
        val lname: Any,
        val login_count: Any,
        val mobile: Any,
        val reg_type: Any,
        val title: Any,
        val updated_at: String
    )
}