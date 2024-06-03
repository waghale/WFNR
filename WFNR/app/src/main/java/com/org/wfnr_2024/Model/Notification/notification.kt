package com.org.wfnr_2024.Model.Notification

data class notification(
    val `data`: List<Data>,
    val message: String,
    val status_code: Int
) {

}