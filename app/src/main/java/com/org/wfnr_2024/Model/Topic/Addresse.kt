package com.org.wfnr_2024.Model.Topic

import com.google.gson.annotations.SerializedName

data class Addresse(
    @SerializedName("@id") val id: String?,
    val city: String,
    val company: String,
    val country: String,
    val department: Any,
    val displayValue: String,
    val state: Any,
    val street: String,
    val zip: String
)