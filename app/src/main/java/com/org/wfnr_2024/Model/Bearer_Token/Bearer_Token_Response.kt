package com.org.wfnr_2024.Model.Bearer_Token

import com.google.gson.annotations.SerializedName

data class Bearer_Token_Response(
    val access_token: String,
    val expires_in: Int,
    @SerializedName("not-before-policy")val not_before_policy: Int,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val session_state: String,
    val token_type: String
)