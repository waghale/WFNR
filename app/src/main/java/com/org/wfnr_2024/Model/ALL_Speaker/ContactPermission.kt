package com.org.wfnr_2024.Model.ALL_Speaker

import java.io.Serializable

data class ContactPermission(
    val type: String,
    val answered: Boolean,
    val consent: Boolean,
    val event: String
) :Serializable