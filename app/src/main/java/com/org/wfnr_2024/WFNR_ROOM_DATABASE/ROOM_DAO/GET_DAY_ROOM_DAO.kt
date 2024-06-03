package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room


@Dao
interface GET_DAY_ROOM_DAO {

    @Query("SELECT * FROM Get_Day_Room")
    fun getAll_Day_Room(): LiveData<List<Get_Day_Room>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Day_room_list(day_room_List: List<Get_Day_Room>)


    @Query("DELETE FROM Get_Day_Room")
    suspend fun delete_All_Get_Day_Room()
}