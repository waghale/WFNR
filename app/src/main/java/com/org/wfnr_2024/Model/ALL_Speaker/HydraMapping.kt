package com.org.wfnr_2024.Model.ALL_Speaker

import java.io.Serializable

data class HydraMapping(
    val type: String,
    val `property`: String,
    val required: Boolean,
    val variable: String
):Serializable