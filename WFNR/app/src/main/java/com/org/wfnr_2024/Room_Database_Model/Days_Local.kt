package com.org.wfnr_2024.Room_Database_Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.org.wfnr_2024.Model.SessionModel.HydraMember
import com.org.wfnr_2024.Model.SessionModel.HydraSearch
import com.org.wfnr_2024.Model.SessionModel.HydraView


@Entity(tableName = "Days_Local")
data class Days_Local
    (
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val context: String
  /*  @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,*/
  /*  @SerializedName("hydra:member") val member: ArrayList<com.org.wfnr_2024.Model.Days.HydraMember>,
    @SerializedName("hydra:search") val search: com.org.wfnr_2024.Model.Days.HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: com.org.wfnr_2024.Model.Days.HydraView*/
)
