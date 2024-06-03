package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question

@Dao
interface TOPIC_QUESTION_DAO{


    @Query("SELECT * FROM Topic_Question")
    fun getAll_Topic_Question(): LiveData<List<Topic_Question>>

    @Query("SELECT * FROM Topic_Question WHERE date = :selectedDate")
    fun getTopic_QuestionByDate(selectedDate: String): LiveData<List<Topic_Question>>

    @Query("SELECT * FROM Topic_Question WHERE wcnr_id = :member_id")
    fun getTopic_QuestionByMemberID(member_id: String): LiveData<List<Topic_Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_Question_list(Topic_Question_List: List<Topic_Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert_Topic_Question(Topic_Question_List: Topic_Question)


    @Query("DELETE FROM Topic_Question")
    suspend fun delete_All_Topic_Question()
}