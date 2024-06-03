package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question


@Dao
interface TOPIC_NOTE_DAO {


    @Query("SELECT * FROM Topic_Notes")
    fun getAll_Topic_Notes(): LiveData<List<Topic_Notes>>

    @Query("SELECT * FROM Topic_Notes WHERE date = :selectedDate")
    fun getTopic_NotesByDate(selectedDate: String): LiveData<List<Topic_Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_Notes_list(Topic_Notes_List: List<Topic_Notes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_Notes(Topic_Notes_List: Topic_Notes)


    @Query("DELETE FROM Topic_Notes")
    suspend fun delete_All_Topic_Notes()


    @Query("SELECT * FROM Topic_Notes WHERE wcnr_id = :member_id")
    fun getTopic_NotesByMemberID(member_id: String): LiveData<List<Topic_Notes>>
}