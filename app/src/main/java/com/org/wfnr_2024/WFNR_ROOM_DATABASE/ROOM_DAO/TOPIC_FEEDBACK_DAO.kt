package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_FeedBack


@Dao
interface TOPIC_FEEDBACK_DAO {


    @Query("SELECT * FROM Topic_FeedBack")
    fun getAll_Topic_FeedBack(): LiveData<List<Topic_FeedBack>>

    @Query("SELECT * FROM Topic_FeedBack WHERE date = :selectedDate")
    fun getTopic_FeedBackByDate(selectedDate: String): LiveData<List<Topic_FeedBack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_FeedBack_list(Topic_Topic_FeedBack: List<Topic_FeedBack>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_FeedBack(Topic_FeedBack_List: Topic_FeedBack)


    @Query("DELETE FROM Topic_FeedBack")
    suspend fun delete_All_Topic_FeedBack()
}