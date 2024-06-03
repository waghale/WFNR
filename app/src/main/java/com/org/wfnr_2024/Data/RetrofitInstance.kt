package org.impactindiafoundation.iifllemeddocket.Data

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.org.wfnr_2024.Utils.ConstantsApp




class RetrofitInstance {

    companion object {
        private val gson = GsonBuilder()
            .setLenient()
            .create()

        private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private fun createRetrofit(baseUrl: String): Retrofit {
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(ConnectionPool(5, 10, TimeUnit.MINUTES))
                .build()

                //.connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))//replaced

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }



        private val productionRetrofit by lazy {
            //createRetrofit("https://impactindiafoundation.org/ImpactWebService/rest/LLEwebcall/")
            createRetrofit(ConstantsApp.BASE_URL1)
        }
        private val production1Retrofit by lazy {
            //createRetrofit("https://impactindiafoundation.org/ImpactWebService/rest/LLEwebcall/")
            createRetrofit(ConstantsApp.BASE_URL2)
        }

        private val production2Retrofit by lazy {
            //createRetrofit("https://impactindiafoundation.org/ImpactWebService/rest/LLEwebcall/")
            createRetrofit(ConstantsApp.BASE_URL3)
        }

        private val localRetrofit by lazy {
            //createRetrofit("http://localhost:8095/ImpactWebService/rest/LLEwebcall/")
       //  createRetrofit(ConstantsApp.LOCAL_URL)

           createRetrofit(ConstantsApp.BASE_URL)
        }

        val productionApi: WFNR_API by lazy {
            productionRetrofit.create(WFNR_API::class.java)
        }
        val production1Api: WFNR_API by lazy {
            production1Retrofit.create(WFNR_API::class.java)
        }
        val production2Api: WFNR_API by lazy {
            production2Retrofit.create(WFNR_API::class.java)
        }

        val localApi: WFNR_API by lazy {
            localRetrofit.create(WFNR_API::class.java)
        }
    }
}
