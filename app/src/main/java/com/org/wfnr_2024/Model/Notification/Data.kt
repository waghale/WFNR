package com.org.wfnr_2024.Model.Notification

data class Data(
    val attachment: String,
    val content: String,
    val created_at: String,
    val date: String,
    val id: Int,
    val isScheduled: Int,
    val isSent: Int,
    val link_to: String,
    val sent_to_android: Int,
    val sent_to_ios: Int,
    val sent_to_total: Any,
    val time: String,
    val title: String,
    val updated_at: String
)