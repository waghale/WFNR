package com.org.wfnr_2024.Room_Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.org.wfnr_2024.Room_Database.ROOM_DAO.Days_DAO
import com.org.wfnr_2024.Room_Database.ROOM_DAO.SessionDataClass_DAO
import com.org.wfnr_2024.Room_Database_Model.Days_Local
import com.org.wfnr_2024.Room_Database_Model.SessionDataClass_Local

@Database(entities = [SessionDataClass_Local::class, Days_Local::class], version = 2, exportSchema = false)
abstract class WFNR_Room_Database : RoomDatabase() {

    abstract fun SessionDataClass_DAO(): SessionDataClass_DAO
    abstract fun Days_DAO(): Days_DAO

    companion object {
        @Volatile
        private var database: WFNR_Room_Database? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Drop the existing Days_Local table (if it exists)
                database.execSQL("DROP TABLE IF EXISTS Days_Local")

                // Create a new Days_Local table with updated schema
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS Days_Local (" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "@context TEXT NOT NULL," +
                            "@id TEXT NOT NULL," +
                            "@type TEXT NOT NULL," +
                            "totalItems INTEGER NOT NULL" +
                            ")"
                )
            }
        }

        fun getDatabase(context: Context): WFNR_Room_Database {
            return database ?: synchronized(this) {
                database ?: buildDatabase(context).also { database = it }
            }
        }

        private fun buildDatabase(context: Context): WFNR_Room_Database {
            return Room.databaseBuilder(
                context.applicationContext,
                WFNR_Room_Database::class.java,
                "WFNR_Room_Database"
            ).addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}
