package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session


@Dao
interface SESSION_DAO {

    @Query("SELECT * FROM Session")
    fun get_Session(): LiveData<List<Session>>

    @Query("SELECT * FROM Session WHERE date = :selectedDate")
    fun get_Sessions_ByDate(selectedDate: String): LiveData<List<Session>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Session(session_List: List<Session>)


    @Query("DELETE FROM Session")
    suspend fun delete_All_Session()


}