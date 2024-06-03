package com.org.wfnr_2024.Utils

import com.org.wfnr_2024.Interface.ApiService
import com.org.wfnr_2024.Interface.ApiService2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient2 {

    private const val BASE_URL = "https://program-frontend-api.stage.conventus.de/api/"

    val apiService: ApiService2 by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService2::class.java)
    }
}