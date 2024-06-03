package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL

data class QuestionSaveResult(

val success:Boolean,
val wcnrId: String,
val session_id:String,
val topic_note: String,
val topic_question: String,
val room_name: String

)
