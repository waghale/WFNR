package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL

data class FeedbackSaveResult(
    val success:Boolean,
    val wcnrId: String,
    val topicRating: String,
    val topicId: String,
    val wcnrName: String
)
