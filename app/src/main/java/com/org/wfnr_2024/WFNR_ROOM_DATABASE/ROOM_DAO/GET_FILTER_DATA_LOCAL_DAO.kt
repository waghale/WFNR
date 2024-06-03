package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local


@Dao
interface GET_FILTER_DATA_LOCAL_DAO {

    @Query("SELECT * FROM Get_Filter_Data_Local")
    fun getGET_FILTER_DATA_LOCAL(): LiveData<List<Get_Filter_Data_Local>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_GET_FILTER_DATA_LOCAL_list(day_room_List: List<Get_Filter_Data_Local>)


    @Query("DELETE FROM Get_Filter_Data_Local")
    suspend fun delete_All_GET_FILTER_DATA_LOCAL()

    @Query("SELECT * FROM Get_Filter_Data_Local WHERE first_name LIKE :query || '%' OR last_name LIKE :query || '%'")
    fun getFilteredData(query: String): LiveData<List<Get_Filter_Data_Local>>
}