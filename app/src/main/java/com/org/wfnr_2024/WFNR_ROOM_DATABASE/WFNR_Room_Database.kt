package com.org.wfnr_2024.WFNR_ROOM_DATABASE

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.ChairConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.DateConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.ProgramPointsConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.RoomSessionConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.SessionTypeConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.SubtitleConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.date3Converters
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.LOGIN_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_FEEDBACK_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_ITINERARY_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_NOTE_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_QUESTION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.RoomOrderListConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.ChairListConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.GetSessionListConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.LanguagesListConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.ProgramPointListConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.RoomConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.SessionAdditionConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.TopicsConverter
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_FeedBack
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Import your DAOs and entity classes here

@Database(
    entities = [
        Get_Day_Room::class,
        Get_Session::class,
        Get_Filter_Data_Local::class,
        Topic_Notes::class,
        Topic_Question::class,
        Topic_FeedBack::class,
        Topic_Itinerary::class,
        LOGIN_LOCAL::class,
    Session::class
    ],
    version = 17,
    exportSchema = false
)
@TypeConverters(
    RoomOrderListConverter::class,
    ChairListConverter::class,
    LanguagesListConverter::class,
    ProgramPointListConverter::class,
    RoomConverter::class,
    SessionAdditionConverter::class,
    TopicsConverter::class,
    GetSessionListConverter::class,
    ChairConverter::class,
    DateConverter::class,
    ProgramPointsConverter::class,
    RoomSessionConverter::class,
    SessionTypeConverter::class

)
abstract class WFNR_Room_Database : RoomDatabase() {

    abstract fun GET_DAY_ROOM_DAO(): GET_DAY_ROOM_DAO
    abstract fun GET_SESSION_DAO(): GET_SESSION_DAO
    abstract fun GET_FILTER_DATA_LOCAL_DAO(): GET_FILTER_DATA_LOCAL_DAO
    abstract fun TOPIC_NOTE_DAO(): TOPIC_NOTE_DAO
    abstract fun TOPIC_QUESTION_DAO(): TOPIC_QUESTION_DAO
    abstract fun TOPIC_FEEDBACK_DAO(): TOPIC_FEEDBACK_DAO
    abstract fun TOPIC_ITINERARY_DAO(): TOPIC_ITINERARY_DAO
    abstract fun LOGIN_LOCAL_DAO(): LOGIN_LOCAL_DAO
    abstract fun SESSION_DAO():SESSION_DAO

    companion object {
        @Volatile
        private var database: WFNR_Room_Database? = null

        fun getDatabase(context: Context): WFNR_Room_Database {
            return database ?: synchronized(this) {
                database ?: buildDatabase(context).also { database = it }
            }
        }

        private fun buildDatabase(context: Context): WFNR_Room_Database {
            return Room.databaseBuilder(
                context.applicationContext,
                WFNR_Room_Database::class.java, "WFNR_Room_Database"
            )
                .addMigrations(
                    MIGRATION_6_7,
                    MIGRATION_7_8,
                    MIGRATION_9_10,
                    MIGRATION_10_11,
                    MIGRATION_13_14,
                    MIGRATION_14_15,
                    MIGRATION_15_16
                )
                .fallbackToDestructiveMigration()
                .build()
        }

        // Define your migrations here

        private val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Login_Local ADD COLUMN member_id TEXT")
                database.execSQL("ALTER TABLE Topic_FeedBack ADD COLUMN topic_id TEXT")
            }
        }

        private val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Topic_FeedBack ADD COLUMN topic_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Question ADD COLUMN session_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Question ADD COLUMN room_name TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Get_Session ADD COLUMN id1 TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_10_11 = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN topic_id TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN is_fav TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Question ADD COLUMN topic_name TEXT NOT NULL DEFAULT ''")
               // database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN room_name TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_13_14 = object : Migration(13, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN room_name TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_14_15 = object : Migration(14, 15) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN begin_formated TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN end_formated TEXT NOT NULL DEFAULT ''")
            }
        }


        private val MIGRATION_15_16 = object : Migration(15, 16) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN begin_date_formated TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN end_date_formated TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN begin_time_formated TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE Topic_Itinerary ADD COLUMN end_time_formated TEXT NOT NULL DEFAULT ''")
            }
        }
    }

    // Add a suspend function to perform database operations on a background thread

    suspend fun performDatabaseOperation(operation: suspend () -> Unit) {
        withContext(Dispatchers.IO) {
            operation()
        }
    }

    suspend fun <T> performDatabaseOperation5(databaseOperation: () -> LiveData<T>): LiveData<T> {
        return withContext(Dispatchers.IO) {
            databaseOperation.invoke()
        }
    }

    suspend fun <T> performDatabaseOperation1(block: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            block.invoke()
        }
    }

    suspend fun <T> performDatabaseOperation2(operation: suspend () -> T): T {
        return operation()
    }

    suspend fun <T> performDatabaseOperation3(
        operation: suspend () -> T
    ): T {
        return withContext(Dispatchers.IO) {
            try {
                operation()
            } catch (e: Exception) {
                throw RuntimeException("Database operation failed: ${e.message}")
            }
        }
    }

    suspend fun <T> performDatabaseOperation6(databaseOperation: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            databaseOperation.invoke()
        }
    }
}
