package com.org.wfnr_2024.SQL_Database.CONVERTERS.Days

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.SQL_Database.DayEntity

@Dao
interface DaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayEntity>)

    @Query("SELECT * FROM days_table")
    suspend fun getDays(): List<DayEntity>
}
