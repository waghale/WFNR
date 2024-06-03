package com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes

@Dao
interface TOPIC_ITINERARY_DAO {

    @Query("SELECT * FROM Topic_Itinerary")
    fun getAll_Itinerary(): LiveData<List<Topic_Itinerary>>

    @Query("SELECT * FROM Topic_Itinerary WHERE date = :selectedDate")
    fun getTopic_ItineraryByDate(selectedDate: String): LiveData<List<Topic_Itinerary>>

    @Query("SELECT * FROM Topic_Itinerary WHERE wcnr_id = :member_id")
    fun getTopic_ItineraryByMemberID(member_id: String): LiveData<List<Topic_Itinerary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_Itinerary_list(Topic_Itinerary_List: List<Topic_Itinerary>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert_Topic_Itinerary(Topic_Itinerary_List: Topic_Itinerary)


    @Query("DELETE FROM Topic_Itinerary")
    suspend fun delete_All_Topic_Itinerary()

    @Query("DELETE FROM Topic_Itinerary WHERE topic_id = :topicId")
    suspend fun deleteByTopicId(topicId: String)

   /* @Query("SELECT * FROM Topic_Itinerary ORDER BY date ASC, `begin` ASC LIMIT 1")
    suspend fun getEarliestItinerary(): Topic_Itinerary?*/

    @Query("SELECT * FROM Topic_Itinerary ORDER BY begin_date_formated ASC, `begin_time_formated` ASC LIMIT 1")
    suspend fun getEarliestItinerary(): Topic_Itinerary?

    @Query("SELECT * FROM Topic_Itinerary WHERE begin_formated >= :sqlDateTime")
    fun getRecordsBeforeDateTime(sqlDateTime: String): List<Topic_Itinerary>




}