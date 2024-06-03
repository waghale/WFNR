package com.org.wfnr_2024.Interface



import com.org.wfnr_2024.Model.Topic.Topic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Topic_service {
    @GET("program_points")
    fun getTopics(
        @Query("page") page: Int,
        @Query("itemsPerPage") itemsPerPage: Int,
        @Query("session") sessionID: String
    ): Call<Topic>
}


