package com.org.wfnr_2024.WFNR_ROOM_DATABASE

import android.app.ProgressDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.CredentialResult
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.FeedbackSaveResult
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.ItinerarySaveResult
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.QuestionSaveResult
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_FeedBack
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WFNR_LOCAL_ViewModel(private val repository: WFNR_LOCAL_Repository) : ViewModel() {



    val get_All_Topic:LiveData<List<Topic_Notes>> = repository.getAll_Topics

    private val _memberId = MutableLiveData<String>()
    val get_All_ITINERARY:LiveData<List<Topic_Itinerary>> = repository.getAll_Itinerary

    val get_All_Questions:LiveData<List<Topic_Question>> = repository.get_all_Question
    val getAll_DAy_Room: LiveData<List<Get_Day_Room>> = repository.getAll_Day_Room
    val getAll_Session: LiveData<List<Get_Session>> = repository.getAll_Session

    val get_All_Session: LiveData<List<Session>> = repository.get_All_Session
    val getAll_FilterData: LiveData<List<Get_Filter_Data_Local>> = repository.getAll_FilterData

    val getAll_Member: LiveData<List<LOGIN_LOCAL>> = repository.getAll_member

    private val _filteredData = MutableLiveData<List<Get_Filter_Data_Local>>()
    val filteredData: LiveData<List<Get_Filter_Data_Local>> = _filteredData

    private val _saveSuccess = MutableLiveData<String>()
    val saveSuccess: LiveData<String> get() = _saveSuccess

    val _insertSuccessQuestion = MutableLiveData<String>()
    val insertSuccessQuestion: LiveData<String> get() = _insertSuccessQuestion

    private val _saveFeedbackSuccess = MutableLiveData<FeedbackSaveResult>()
    val saveFeedBackSuccess: LiveData<FeedbackSaveResult> = _saveFeedbackSuccess

    private val _saveQuestionSuccess = MutableLiveData<QuestionSaveResult>()
    val saveQuestionSuccess: LiveData<QuestionSaveResult> = _saveQuestionSuccess

    private val _saveItinararySuccess = MutableLiveData<Topic_Itinerary>()
    val saveItinararySuccess: LiveData<Topic_Itinerary> = _saveItinararySuccess

    private val _credentialsExistLiveData = MutableLiveData<Boolean>()
    val credentialsExistLiveData: LiveData<Boolean> = _credentialsExistLiveData

    private val _credentialsLiveData = MutableLiveData<CredentialResult>()
    val credentialsLiveData: LiveData<CredentialResult> = _credentialsLiveData

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _EmailID = MutableLiveData<String>()
    val emailID: LiveData<String> get() = _EmailID

    private val _sessionsByDate = MutableLiveData<List<Get_Session>>()
   // val sessionsByDate: LiveData<List<Get_Session>> get() = _sessionsByDate





    fun insert_Day_room_list(
        progressDialog: ProgressDialog,
        day_room_List: MutableList<Get_Day_Room>
    ) {
        viewModelScope.launch {

            try {
                progressDialog.show()
                repository.insert_Day_room_list(day_room_List)

                _saveSuccess.postValue("Success")

                progressDialog.dismiss()
            } catch (e: Exception) {
                progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }
    }

    fun insert_Day_room_list(

        day_room_List: MutableList<Get_Day_Room>
    ) {
        viewModelScope.launch {

            try {
               // progressDialog.show()
                repository.insert_Day_room_list(day_room_List)

                _saveSuccess.postValue("Success")

               // progressDialog.dismiss()
            } catch (e: Exception) {
               // progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }
    }

    fun delete_All_Get_Day_Room() {
        viewModelScope.launch {
            repository.delete_All_Get_Day_Room()
        }
    }

    fun delete_All_Session() {
        viewModelScope.launch {
            repository.delete_All_Session()
        }
    }

    fun insert_Session_list(
        progressDialog: ProgressDialog,
        session_List: MutableList<Get_Session>
    ) {
        viewModelScope.launch {

            try {
                progressDialog.show()
                repository.insert_session_list(session_List)
                _saveSuccess.postValue("Success")

                progressDialog.dismiss()
            } catch (e: Exception) {
                progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }
    }

    fun insert_Session_list(

        session_List: MutableList<Get_Session>
    ) {
        viewModelScope.launch {

            try {
                // progressDialog.show()
                repository.insert_session_list(session_List)
                _saveSuccess.postValue("Success")

                //progressDialog.dismiss()
            } catch (e: Exception) {
                // progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }
    }

    fun insert_filter_Data(
        progressDialog: ProgressDialog,
        filterDataList: MutableList<Get_Filter_Data_Local>
    ) {

        viewModelScope.launch {

            try {
                progressDialog.show()
                repository.insert_filter_list(filterDataList)

                progressDialog.dismiss()
            } catch (e: Exception) {
                progressDialog.dismiss()
            }
        }


    }

    fun insert_filter_Data(

        filterDataList: MutableList<Get_Filter_Data_Local>
    ) {

        viewModelScope.launch {

            try {
               // progressDialog.show()
                repository.insert_filter_list(filterDataList)

               // progressDialog.dismiss()
            } catch (e: Exception) {
                //progressDialog.dismiss()
            }
        }


    }

    fun delete_All_filter_data() {
        viewModelScope.launch {
            repository.delete_All_filter_data()
        }
    }

    fun getFilteredData(query: String): LiveData<List<Get_Filter_Data_Local>> {
        return repository.getFilteredData(query)
    }

    fun insertTopicNote(topicNoteRequest: Topic_Notes) {

        viewModelScope.launch {

            try {

                repository.insertTopicNote(topicNoteRequest)
                _saveSuccess.postValue("Success")


            } catch (e: Exception) {

                _saveSuccess.postValue("Failure")

            }

        }

    }

    fun saveRegistrationDataLocal(registrationDataLocalRequest: LOGIN_LOCAL) {

        viewModelScope.launch {

            try {

                repository.saveRegistrationDataLocal(registrationDataLocalRequest)
                _saveSuccess.postValue("Success")


            } catch (e: Exception) {

                _saveSuccess.postValue("Failure")

            }


        }

    }

    fun storeFeedBack(topicFeedback: Topic_FeedBack) {

        viewModelScope.launch {

           /* try {

                repository.storeFeedBack(topicFeedback)
                _saveSuccess.postValue("Success")


            } catch (e: Exception) {

                _saveSuccess.postValue("Failure")

            }*/

            try {
                repository.storeFeedBack(topicFeedback)
                // Extract the required data from the feedback object
                val result = FeedbackSaveResult(
                    success = true,
                    topicFeedback.wcnr_id,
                    topicFeedback.topic_rating,
                    topicFeedback.topic_id,
                    topicFeedback.wcnr_name
                )
                _saveFeedbackSuccess.postValue(result)
            } catch (e: Exception) {
                val result = FeedbackSaveResult(
                    success = false,
                    "",
                    "",
                    "",
                    ""
                )
                _saveFeedbackSuccess.postValue(result)
            }




        }

    }

     fun storeQuestionLocal(Topic_Question: Topic_Question) {

         viewModelScope.launch {

             try {
                 repository.storeQuestionLocal(Topic_Question)
                 // Extract the required data from the feedback object
                 val result = QuestionSaveResult(
                     success = true,
                     Topic_Question.wcnr_id,
                     Topic_Question.session_id,
                     Topic_Question.topic_note,
                     Topic_Question.topic_question,
                     Topic_Question.room_name
                 )
                 _saveQuestionSuccess.postValue(result)
             } catch (e: Exception) {
                 val result = QuestionSaveResult(
                     success = false,
                     "",
                     "",
                     "",
                     "",
                     ""
                 )
                 _saveQuestionSuccess.postValue(result)
             }

         }

    }

    fun Insert_Itinerary_Local(Topic_Itinerary: Topic_Itinerary) {
        viewModelScope.launch {

            try {

                repository.Insert_Itinerary_Local(Topic_Itinerary)
                _saveSuccess.postValue("Success")


            } catch (e: Exception) {

                _saveSuccess.postValue("Failure")

            }

        }

    }

    fun Delete_Itinerary_Local(id1: String) {

        viewModelScope.launch {

            try {

                repository.Delete_Itinerary_Local(id1)
                _saveSuccess.postValue("Success")


            } catch (e: Exception) {

                _saveSuccess.postValue("Failure")

            }

        }

    }

    fun deleteData3(date: String) {

        viewModelScope.launch {
            repository.deleteData3(date)
        }

    }

    val topicItineraries: LiveData<List<Topic_Itinerary>> = _memberId.switchMap { memberId ->
        repository.getTopicItineraryByMemberID(memberId)
    }

    val topicQuestion: LiveData<List<Topic_Question>> = _memberId.switchMap { memberId ->
        repository.getTopicQuestionsByMemberID(memberId)
    }

    val topicNotes: LiveData<List<Topic_Notes>> = _memberId.switchMap { memberId ->
        repository.getTopicNotesByMemberID(memberId)
    }

    fun setMemberId(memberId: String) {
        _memberId.value = memberId
    }

    fun setQuestionMemberId(memberId: String) {

        _memberId.value = memberId

    }
    fun setNotesMemberId(memberId: String) {

        _memberId.value = memberId

    }



   /* fun checkCredentialsExist(wcnrRegNo: String, userEmailId: String) {
        val credentialsExistLiveData = repository.checkCredentialsExist(wcnrRegNo, userEmailId)
        credentialsExistLiveData.observeForever { credentialsExist ->
            _credentialsExistLiveData.postValue(credentialsExist)
        }
    }*/

    fun delete_All_member() {
        viewModelScope.launch {
            repository.delete_All_member()
        }
    }

    fun checkCredentialsExist(wcnrRegNo: String, userEmailId: String): LiveData<CredentialResult> {
        //val credentialsExistLiveData = MutableLiveData<CredentialResult>()
        repository.checkCredentialsExist(wcnrRegNo, userEmailId).observeForever { credentialsExist ->
            val result = if (credentialsExist) {
                CredentialResult(success = true,member_id = wcnrRegNo, email = userEmailId)
            } else {
                CredentialResult(success = false,member_id = "", email = "")
                //null
            }
            _credentialsLiveData.postValue(result)
        }
        return _credentialsLiveData
    }




   /* fun fetchLoginLocalByCredentials(wcnrRegNo: String, userEmailId: String) {
        viewModelScope.launch {
            val loginLocal = repository.getLoginLocalByCredentials(wcnrRegNo, userEmailId)
            _loginLiveData.value = loginLocal
        }
    }*/



    fun getLoginLocalByCredentials(wcnrRegNo: String, userEmailId: String): LiveData<LOGIN_LOCAL?> {
        return liveData(viewModelScope.coroutineContext) {
            val loginLocal = repository.getLoginLocalByCredentials(wcnrRegNo, userEmailId)
            emitSource(loginLocal)
        }
    }



    fun Insert_ItineraryList_Local(topicItineraryList: MutableList<Topic_Itinerary>) {

        viewModelScope.launch {

            try {
                // progressDialog.show()
                repository.Insert_ItineraryList_Local(topicItineraryList)
                _saveSuccess.postValue("Success")

                //progressDialog.dismiss()
            } catch (e: Exception) {
                // progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }

    }

    fun deleteAllItineraryData() {
        viewModelScope.launch {
            repository.deleteAllItineraryData()
        }
    }

    fun deleteAllNotesData() {
        viewModelScope.launch {
            repository.deleteAllNotesData()
        }
    }

    fun Insert_Notes_Local(topicItineraryList: MutableList<Topic_Notes>) {

        viewModelScope.launch {

            try {
                // progressDialog.show()
                repository.Insert_Notes_Local(topicItineraryList)
                _saveSuccess.postValue("Success")

                //progressDialog.dismiss()
            } catch (e: Exception) {
                // progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }

    }




   /* val sessionsByDate: LiveData<List<Get_Session>> = _selectedDate.switchMap { date ->
        repository.getSessionsByDate(date)
    }*/
    val sessionsByDate: LiveData<List<Session>> = _selectedDate.switchMap { date ->
        repository.getSessionsByDate(date)
    }

  /*  val LoginDataByDate: LiveData<List<Get_Session>> = _selectedDate.switchMap { date ->
        repository.getSessionsByDate(date)
    }*/

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun getLoginLocalByEmail(emailId: String): LiveData<LOGIN_LOCAL?> {
        val result = MutableLiveData<LOGIN_LOCAL?>()
        viewModelScope.launch(Dispatchers.IO) {
            val loginLocal = repository.getLoginLocalByEmail(emailId)
            withContext(Dispatchers.Main) {
                result.value = loginLocal
            }
        }
        return result
    }

    fun getRecordsBeforeDateTime(sqlDateTime: String) {
        viewModelScope.launch {
            repository.getRecordsBeforeDateTime(sqlDateTime)
        }
    }

    fun insertSession(sessions: List<Session>) {

        viewModelScope.launch {

            try {
                // progressDialog.show()
                repository.insertSession(sessions)

                _saveSuccess.postValue("Success")

                // progressDialog.dismiss()
            } catch (e: Exception) {
                // progressDialog.dismiss()
                _saveSuccess.postValue("Failure")
            }


        }

    }

    fun delete_session() {
        viewModelScope.launch {
            repository.delete_Session()
        }
    }

    fun insertQuestionLocal(questionMutableList: MutableList<Topic_Question>?) {

        viewModelScope.launch {

            try {
                // progressDialog.show()
                repository.Insert_Question_Local(questionMutableList)
                _insertSuccessQuestion.postValue("Success")

                //progressDialog.dismiss()
            } catch (e: Exception) {
                // progressDialog.dismiss()
               // _saveSuccess.postValue("Failure")
            }


        }

    }

    fun delete_All_Question() {
        viewModelScope.launch {
            repository.delete_All_Question()
        }
    }




}




