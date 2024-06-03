package com.org.wfnr_2024.ViewModel

import com.org.wfnr_2024.Model.Bearer_Token.Bearer_Token_Request
import com.org.wfnr_2024.Model.FEEDBACK.FeedBack_Request
import com.org.wfnr_2024.Model.FEEDBACK.Send_FeedBack_To_Server_Request
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_REQUEST
import com.org.wfnr_2024.Model.GET_FILTER_DATA.GET_FILTER_REQUEST
import com.org.wfnr_2024.Model.GET_QUESTIONS.Get_Question_Request
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_REQUEST
import com.org.wfnr_2024.Model.ITINERARY.Send_Favourite_Request
import com.org.wfnr_2024.Model.Login.Registration_Request
import com.org.wfnr_2024.Model.Note.Topic_Note_Request
import com.org.wfnr_2024.Model.Topic_Question_Server.Topic_Question_Request
import com.org.wfnr_2024.Model.VerifyParticipent.Verify_Participent_Request
import org.impactindiafoundation.iifllemeddocket.Data.RetrofitInstance


class WFNRRespository() {
    suspend fun getDays(getDaysRequest: GET_DAYS_REQUEST) =RetrofitInstance.localApi.getDays(getDaysRequest.event)
    suspend fun getSessionRequest1LiveData(sessionRequest1: GET_SESSION_REQUEST)=RetrofitInstance.localApi.getSession1(sessionRequest1.event,sessionRequest1.date,sessionRequest1.groups,sessionRequest1.pagination)
    suspend fun getFilterLiveData(filterRequest: GET_FILTER_REQUEST)=RetrofitInstance.localApi.getFilterData(filterRequest.event,filterRequest.hasChairOrSpeakerEngagements,filterRequest.pagination)
    suspend fun getBearerTokenRequest(bearerTokenRequest: Bearer_Token_Request)=RetrofitInstance.productionApi.getToken(
        bearerTokenRequest.grantType!!,bearerTokenRequest.clientId!!,bearerTokenRequest.clientSecret!!)

    suspend fun getVerify_Participent(verifyParticipentRequest: Verify_Participent_Request)=RetrofitInstance.production1Api.verifyParticipant(verifyParticipentRequest.accept!!,
        verifyParticipentRequest.authorization!!,verifyParticipentRequest.eventId!!,
        verifyParticipentRequest.email!!,verifyParticipentRequest.participantNumber!!)

    suspend fun storeMember(registrationRequest: Registration_Request)=RetrofitInstance.production2Api.store_member(registrationRequest)
    suspend fun getMember()=RetrofitInstance.production2Api.getMember()
    suspend fun storeFeedBack(feedbackRequest: FeedBack_Request)=RetrofitInstance.production2Api.store_feedback(feedbackRequest)
   suspend fun storeQuestion(topicQuestionRequest: Topic_Question_Request)=RetrofitInstance.production2Api.store_Question(topicQuestionRequest)
   suspend fun store_favourites(data: Send_Favourite_Request)=RetrofitInstance.production2Api.store_favourites(data)
   suspend fun store_notes(data: Topic_Note_Request)=RetrofitInstance.production2Api.store_notes(data)
   suspend fun storeOverAllFeedback(data: Send_FeedBack_To_Server_Request)=RetrofitInstance.production2Api.storeOverAllFeedback(data)
   suspend fun get_favourites()=RetrofitInstance.production2Api.get_favourites()
    suspend fun get_Notes()=RetrofitInstance.production2Api.get_notes()
    suspend fun getSession22(date: String) =RetrofitInstance.production2Api.getSession22(date)
    suspend fun getQuestionMemberWise(questionRequest: Get_Question_Request)=RetrofitInstance.production2Api.getQuestionMemberWise(questionRequest)


    /*    suspend fun getVerify_Participent(verifyParticipentRequest: Verify_Participent_Request)=RetrofitInstance.production1Api.verifyParticipant(
            verifyParticipentRequest.authorization!!,verifyParticipentRequest.eventId!!,
            verifyParticipentRequest.email!!,verifyParticipentRequest.participantNumber!!)*/




}