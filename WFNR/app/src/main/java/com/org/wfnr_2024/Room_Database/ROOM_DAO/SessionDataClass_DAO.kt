package com.org.wfnr_2024.Room_Database.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.Room_Database_Model.SessionDataClass_Local
@Dao
interface SessionDataClass_DAO {

    @Query("SELECT * FROM SessionDataClass_Local")
    fun getAllSessionData(): LiveData<List<SessionDataClass_Local>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(sessionDataClass_Local: SessionDataClass_Local)

    @Delete
    fun deleteSession(sessionDataClass_Local: SessionDataClass_Local)
}