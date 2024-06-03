package com.org.wfnr_2024.Model.Login

data class Registration_Response(
    val errors: Errors,
    val message: String,
    val status_code: Int
) {
    data class Errors(
        val WFNR_reg_no: List<String>,
        val email: List<String>
    )
}