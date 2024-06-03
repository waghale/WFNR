package com.org.wfnr_2024.Interface

import com.org.wfnr_2024.Model.Days.Day_get_api
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DaysService {

    @GET("days")
    fun getDays(
        @Query("page") page: Int,
        @Query("itemsPerPage") itemsPerPage: Int,
        @Query("pagination") pagination: Boolean,
        @Query("event") eventId: String
    ): Call<Day_get_api>

}
