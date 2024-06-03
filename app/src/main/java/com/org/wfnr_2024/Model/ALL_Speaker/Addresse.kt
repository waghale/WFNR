package com.org.wfnr_2024.Model.ALL_Speaker

import java.io.Serializable

data class Addresse(
    val type: String,
    val city: String,
    val company: String,
    val country: String,
    val department: Any,
    val displayValue: String,
    val state: Any,
    val street: String,
    val zip: String
):Serializable