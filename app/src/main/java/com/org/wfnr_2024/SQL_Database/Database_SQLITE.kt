package com.org.wfnr_2024.SQL_Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.org.wfnr_2024.SQL_Database.CONVERTERS.ChairListConverter
import com.org.wfnr_2024.SQL_Database.CONVERTERS.Days.DaysDao
import com.org.wfnr_2024.SQL_Database.CONVERTERS.ObjectListConverter
import com.org.wfnr_2024.SQL_Database.CONVERTERS.ProgramPointListConverter
import com.org.wfnr_2024.SQL_Database.CONVERTERS.RoomAdditionTypeConverter
import com.org.wfnr_2024.SQL_Database.CONVERTERS.RoomConverter
import com.org.wfnr_2024.SQL_Database.CONVERTERS.Session.SessionDataDao
import com.org.wfnr_2024.SQL_Database.CONVERTERS.SessionAdditionConverter
import com.org.wfnr_2024.SQL_Database.ENTITY.Room.RoomEntity
import com.org.wfnr_2024.SQL_Database.ENTITY.Session.SessionDataEntity
import com.org.wfnr_2024.SQL_Database.Room.RoomDao

@Database(entities = [DayEntity::class, SessionDataEntity::class, RoomEntity::class], version = 21, exportSchema = false)
@TypeConverters(ChairListConverter::class,ObjectListConverter::class,ProgramPointListConverter::class,RoomConverter::class,SessionAdditionConverter::class,
    RoomAdditionTypeConverter::class)

abstract class Database_SQLITE : RoomDatabase() {

    abstract fun daysDao(): DaysDao
    abstract fun sessionDataDao(): SessionDataDao
    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: Database_SQLITE? = null

        fun getDatabase(context: Context): Database_SQLITE {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database_SQLITE::class.java,
                    "WFNR_database" // Specify your database name here
                ).fallbackToDestructiveMigration().build() // Use
                INSTANCE = instance
                instance
            }
        }
    }
}

