package com.org.wfnr_2024.Model.VerifyParticipent

data class Verify_Participent_Error_Response(
    val errors: Errors
) {
    data class Errors(
        val code: Int,
        val message: String,
        val statusCode: Int,
        val title: String
    )
}