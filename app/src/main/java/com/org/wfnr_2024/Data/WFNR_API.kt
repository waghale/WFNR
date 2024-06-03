package org.impactindiafoundation.iifllemeddocket.Data


import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.Model.ACTIVE_SESSION_TOPIC.Active_Session_Topic_Response
import com.org.wfnr_2024.Model.Bearer_Token.Bearer_Token_Response
import com.org.wfnr_2024.Model.FEEDBACK.FeedBack_Request
import com.org.wfnr_2024.Model.FEEDBACK.FeedBack_Response
import com.org.wfnr_2024.Model.FEEDBACK.Send_FeedBack_To_Server_Request
import com.org.wfnr_2024.Model.FEEDBACK.Send_Feedback_To_Server_Response
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE
import com.org.wfnr_2024.Model.GET_FAVOURITE.Get_Favourite_Response
import com.org.wfnr_2024.Model.GET_FILTER_DATA.GET_FILTER_RESPONSE
import com.org.wfnr_2024.Model.GET_MEMBER.Get_Member_Response
import com.org.wfnr_2024.Model.GET_NOTES.Get_Notes_Response
import com.org.wfnr_2024.Model.GET_QUESTIONS.Get_Question_Request
import com.org.wfnr_2024.Model.GET_QUESTIONS.Get_Question_Response
import com.org.wfnr_2024.Model.GET_QUESTIONS.get_Question_member_wise
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.Model.ITINERARY.Send_Favourite_Request
import com.org.wfnr_2024.Model.ITINERARY.Send_Favourite_Response
import com.org.wfnr_2024.Model.Login.Registration_Request
import com.org.wfnr_2024.Model.Login.Registration_Response
import com.org.wfnr_2024.Model.Note.Topic_Note_Request
import com.org.wfnr_2024.Model.Note.Topic_Note_Response
import com.org.wfnr_2024.Model.Topic_Question_Server.Topic_Question_Request
import com.org.wfnr_2024.Model.Topic_Question_Server.Topic_Question_Response
import com.org.wfnr_2024.Model.VerifyParticipent.Verify_Participent_Response
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface WFNR_API {

    @GET("days?")
    suspend fun getDays(@Query("event") event: String?): Response<GET_DAYS_RESPONSE>

   /* @GET("sessions?")
    suspend fun getSession1(@Query("event")event: String?,
                    @Query("date")date: String?,
                    @Query("groups%5B%5D")groups: List<String>?,
                    @Query("pagination")pagination: Boolean?):Response<GET_SESSION_RESPONSE>*/

    @GET("sessions?")
    suspend fun getSession1(@Query("event")event: String?,
                            @Query("date")date: String?,
                            @Query("groups[]")groups: List<String>?,
                            @Query("pagination")pagination: Boolean?):Response<GET_SESSION_RESPONSE>

    @GET("people?")
    suspend fun getFilterData(@Query("event")event: String?,
                              @Query("hasChairOrSpeakerEngagements")hasChairOrSpeakerEngagements: Boolean?,
                              @Query("pagination")pagination: Boolean?):Response<GET_FILTER_RESPONSE>


    @FormUrlEncoded
    @POST("token")
    suspend fun getToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<Bearer_Token_Response>

    @GET("verify-participant")
    suspend fun verifyParticipant(
        @Header("Accept")accept: String,
        @Header("Authorization")authorization: String,
        @Query("eventId") eventId: String,
        @Query("email") email: String,
        @Query("participantNumber") participantNumber: String

    ): Response<Verify_Participent_Response>

    @POST("store_members")
    suspend fun store_member(@Body registrationRequest: Registration_Request):Response<Registration_Response>

    @POST("get_members")
    suspend fun getMember():Response<Get_Member_Response>

    @POST("store_feedback")
   suspend fun store_feedback(@Body feedbackRequest: FeedBack_Request):Response<FeedBack_Response>

    @POST("store_questions")
    suspend fun store_Question(@Body topicQuestionRequest: Topic_Question_Request):Response<Topic_Question_Response>

    @POST("store_favourites")
    suspend fun store_favourites(@Body data: Send_Favourite_Request):Response<Send_Favourite_Response>

    @POST("store_notes")
    suspend fun store_notes(@Body data: Topic_Note_Request):Response<Topic_Note_Response>

    @POST("store_overallfeedback")
    suspend fun storeOverAllFeedback(@Body data: Send_FeedBack_To_Server_Request):Response<Send_Feedback_To_Server_Response>

    @POST("get_favourites")
    suspend fun get_favourites():Response<Get_Favourite_Response>

    @POST("get_notes")
    suspend fun get_notes():Response<Get_Notes_Response>

    @POST("get_questions")
    suspend fun get_questions():Response<Get_Question_Response>

    @GET("getActiveSessionAndTopic")
    suspend fun getActiveSessionAndTopic():Response<Active_Session_Topic_Response>

  /*  @GET("sessions/22-05-2024")
    suspend fun getSession22(date:String):Response<Sesssion_Model>*/

    @GET("sessions/{date}")
    suspend fun getSession22(@Path("date") date: String): Response<Sesssion_Model>

    @POST("get_questions")
   suspend fun getQuestionMemberWise(@Body questionRequest: Get_Question_Request):Response<get_Question_member_wise>


    /*@GET("verify-participant")
    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJhbGciOiJFUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ1TlUtVmRXdm1rNFlYQ3ZkTzlwQnVQNFg5aVRFX1dIclV4Vk5nMlBPbnVzIn0.eyJleHAiOjE3MTU3Njk1ODAsImlhdCI6MTcxNTc2OTI4MCwianRpIjoiMDQ4MzA5MDEtMWFmNy00N2JjLWFhMGYtYWQ0NzJkMzQ4N2Y1IiwiaXNzIjoiaHR0cHM6Ly9pYW0uY29udmVudHVzLWFwcHMuZGUvYXV0aC9yZWFsbXMvZXh0ZXJuYWxfc2VydmljZXMiLCJhdWQiOlsicmVnYXN1cy1hcGkiLCJhY2NvdW50Il0sInN1YiI6IjZmYzcwNjg4LTM5NzMtNDY1NS05MTVjLWI2YjQ1NjEyNzg0YSIsInR5cCI6IkJlYXJlciIsImF6cCI6IndmbnItYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjBhOWY2N2I2LTVhNzItNDZlZC04OTA4LTUxYjY5ZjFlNzJiOSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlZ2FzdXMtYXBpIjp7InJvbGVzIjpbIlJPTEVfRVhURVJOQUxfU0VSVklDRV9VU0VSIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudEhvc3QiOiIxMTUuMjQ3LjY4LjEzMCIsImNsaWVudElkIjoid2Zuci1hcHAiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtd2Zuci1hcHAiLCJjbGllbnRBZGRyZXNzIjoiMTE1LjI0Ny42OC4xMzAifQ.73rzAuFW2Wibb6hjMVKerfmIkVyJ1HKFcMPGw-wPM8i-gTACLXCcJdzHN3UKGpHw76Vj6bebqH1OyElHzsgA7A"
    )
    suspend fun verifyParticipant(
        @Query("eventId") eventId: String,
        @Query("email") email: String,
        @Query("participantNumber") participantNumber: String
    ): Response<Verify_Participent_Response>*/









}