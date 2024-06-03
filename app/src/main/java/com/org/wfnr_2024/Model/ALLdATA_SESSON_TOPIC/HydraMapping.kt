package com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC

import java.io.Serializable

data class HydraMapping(
    val type: String,
    val `property`: String,
    val required: Boolean,
    val variable: String
):Serializable