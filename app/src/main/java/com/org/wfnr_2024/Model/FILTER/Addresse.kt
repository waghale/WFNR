package com.org.wfnr_2024.Model.FILTER

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Address(
    @SerializedName("@type") val type: String?,
    val city: String?,
    val country: String?,
    val company: Any?,
    val department: Any?,
    val street: Any?,
    val zip: Any?,
    val state: Any?,
    val displayValue: String?
)
