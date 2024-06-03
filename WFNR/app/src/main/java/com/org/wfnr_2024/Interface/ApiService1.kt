package com.org.wfnr_2024.Interface


import com.org.wfnr_2024.Model.Login.login_response
import com.org.wfnr_2024.Model.Notification.notification
import com.org.wfnr_2024.Model.token.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService1 {




    @FormUrlEncoded
    @POST("device_details")
    fun generateToken(
        @Field("device_type") deviceType: String,
        @Field("device_model") deviceModel: String,
        @Field("device_OS") deviceOS: String,
        @Field("location") location: String,
        @Field("imei_no") imeiNo: String,
        @Field("device_token") deviceToken: String
    ): Call<TokenResponse>

    @GET("getChangeByNotification")
    fun getNotifications():Call<notification>
    @POST("login")
    fun login(
        @Query("reg_no") regNo: String,
        @Query("password") password: String
    ): Call<login_response>
}

