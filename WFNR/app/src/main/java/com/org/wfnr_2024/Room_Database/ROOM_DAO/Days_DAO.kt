package com.org.wfnr_2024.Room_Database.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.Room_Database_Model.Days_Local
@Dao

interface Days_DAO {

    @Query("SELECT * FROM Days_Local")
    fun getAllDaysData(): LiveData<List<Days_Local>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDays(daysLocal: Days_Local)

    @Delete
    fun deleteDays(days_local: Days_Local)
}