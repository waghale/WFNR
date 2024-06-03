package com.org.wfnr_2024.SQL_Database.CONVERTERS.Session

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.wfnr_2024.SQL_Database.ENTITY.Session.SessionDataEntity

@Dao
interface SessionDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: kotlin.collections.List<com.org.wfnr_2024.SQL_Database.ENTITY.Session.SessionDataEntity>)

    @Query("SELECT * FROM sessions")
    suspend fun getAllSessions(): List<SessionDataEntity>

    @Query("SELECT * FROM sessions WHERE Id = :sessionId")
    suspend fun getSessionById(sessionId: String): SessionDataEntity?

    @Query("SELECT * FROM sessions WHERE roomId = :roomId")
    suspend fun getSessionsByRoomId(roomId: String): List<SessionDataEntity>

    @Query("DELETE FROM sessions WHERE roomId = :roomId")
    suspend fun deleteSessionsByRoomId(roomId: String)

    @Query("DELETE FROM sessions")
    suspend fun deleteAllSessions()

    // Add other queries as needed

}
