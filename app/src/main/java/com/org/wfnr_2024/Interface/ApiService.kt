package com.org.wfnr_2024.Interface


import com.org.wfnr_2024.Model.ALL_Speaker.Spesker_list
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.HydraMember
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Session_with_Topic
import com.org.wfnr_2024.Model.Days.Day_get_api
import com.org.wfnr_2024.Model.SessionModel.SessionDataModel
import com.org.wfnr_2024.Model.Topic.Topic
import com.org.wfnr_2024.Model.token.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface ApiService {
    @GET("program_items?")
    fun getProgramItems(
        @Query("date") date: String,
        @Query("event") eventId: String,
        @Query("pagination") pagination: Boolean = false
    ): Call<SessionDataModel>


    @GET("days")
    fun getDays(
        @Query("page") page: Int,
        @Query("itemsPerPage") itemsPerPage: Int,
        @Query("pagination") pagination: Boolean,
        @Query("event") eventId: String
    ): Call<Day_get_api>

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

    @GET("program_points")
    fun getTopics(
        @Query("page") page: Int,
        @Query("itemsPerPage") itemsPerPage: Int,
        @Query("pagination") pagination: Boolean = false,
        @Query("session") sessionID: String
    ): Call<Topic>

//    @GET("people")
//    fun getPersonDetails(@Query("personId") personId: String): Call<Filter_session_topic>

    @GET("sessions")
    fun getSessions(
        @Query("event") eventId: String,
        @Query("date") date: String,
        @Query("groups[]") groups: String,
        @Query("pagination") pagination: Boolean = false
    ): Call<Session_with_Topic>

    @GET("people")
    fun getSpeakers(
        @Query("hasChairOrSpeakerEngagements") hasChairOrSpeakerEngagements: Boolean = true,
        @Query("pagination") pagination: Boolean = false,
        @Query("event") eventId: String
    ): Call<Spesker_list>

    @GET("people")
    fun getPersonDetails(
        @Query("people") peopleID: String
    ): Call<Session_with_Topic>

    @GET("sessions")
    fun getSessions(
        @Query("event") eventId: String,
        @Query("date") date: String,
        @Query("groups[]") groups: List<String>,
        @Query("pagination") pagination: Boolean
    ): Call<List<Session_with_Topic>> //
}

