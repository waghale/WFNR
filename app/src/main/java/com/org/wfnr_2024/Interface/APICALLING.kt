package com.org.wfnr_2024.Interface

import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.HydraMember
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APICALLING {
    @GET("sessions")
    fun getSessions(
        @Query("event") eventId: String,
        @Query("date") date: String,
        @Query("groups[]") groups: List<String>,
        @Query("pagination") pagination: Boolean
    ): Call<List<HydraMember>> // Adjust response type as needed
}