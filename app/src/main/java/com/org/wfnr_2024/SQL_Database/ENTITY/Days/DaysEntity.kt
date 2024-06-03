package com.org.wfnr_2024.SQL_Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.org.wfnr_2024.Model.Days.HydraMember

@Entity(tableName = "days_table")
@TypeConverters(Converters::class)

data class DayEntity(
    @PrimaryKey val id: String,
    val context: String,
    val type: String,
    val totalItems: Int,
    val hydraMember: List<HydraMember>
)
