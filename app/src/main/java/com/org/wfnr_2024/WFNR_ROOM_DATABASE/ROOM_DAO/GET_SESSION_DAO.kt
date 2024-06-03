package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session

@Dao
interface GET_SESSION_DAO {

    @Query("SELECT * FROM Get_Session")
    fun getAll_Session(): LiveData<List<Get_Session>>

    @Query("SELECT * FROM Get_Session WHERE date = :selectedDate")
    fun getSessionsByDate(selectedDate: String): LiveData<List<Get_Session>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Session_list(session_List: List<Get_Session>)


    @Query("DELETE FROM Get_Session")
    suspend fun delete_All_Session()

    @Query("DELETE FROM Get_Session WHERE date = :date")
    suspend fun deleteSessionsByDate(date: String)
}