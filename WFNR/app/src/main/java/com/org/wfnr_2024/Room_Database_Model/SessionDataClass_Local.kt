package com.org.wfnr_2024.Room_Database_Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.org.wfnr_2024.Model.SessionModel.HydraMember
import com.org.wfnr_2024.Model.SessionModel.HydraSearch
import com.org.wfnr_2024.Model.SessionModel.HydraView
import com.org.wfnr_2024.Room_Database.HydraMemberTypeConverter
import com.org.wfnr_2024.Room_Database.HydraSearchTypeConverter
import com.org.wfnr_2024.Room_Database.HydraViewTypeConverter

@Entity(tableName = "SessionDataClass_Local")
//@TypeConverters(value = [HydraMemberTypeConverter::class, HydraSearchTypeConverter::class, HydraViewTypeConverter::class])
data class SessionDataClass_Local
    (
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val context: String
  /*  @SerializedName("@context")
     val context: String,
    @SerializedName("@id") val id: String,

    @SerializedName("@type") val type: String,*/
 /*   @SerializedName("hydra:member") val member: List<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView*/
)
