package com.org.wfnr_2024.Utils

import com.org.wfnr_2024.Interface.ApiService1
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient1 {

    private const val BASE_URL = "https://www.telemedocket.com/WFNR-2024/public/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService1 by lazy {
        retrofit.create(ApiService1::class.java)
    }
}
