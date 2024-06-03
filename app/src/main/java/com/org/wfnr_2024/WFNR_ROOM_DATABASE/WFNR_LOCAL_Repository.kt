package com.org.wfnr_2024.WFNR_ROOM_DATABASE

import androidx.lifecycle.LiveData
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.LOGIN_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_FEEDBACK_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_ITINERARY_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_NOTE_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.TOPIC_QUESTION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_FeedBack
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question


class WFNR_LOCAL_Repository(private val GET_DAY_ROOM_DAO: GET_DAY_ROOM_DAO,
                            private val GET_SESSION_DAO: GET_SESSION_DAO,
                            private val GET_FILTER_DATA_LOCAL_DAO: GET_FILTER_DATA_LOCAL_DAO,
                            private val TOPIC_NOTE_DAO: TOPIC_NOTE_DAO,
                            private val TOPIC_QUESTION_DAO: TOPIC_QUESTION_DAO,
                            private val TOPIC_FEEDBACK_DAO: TOPIC_FEEDBACK_DAO,
                            private val TOPIC_ITINERARY_DAO: TOPIC_ITINERARY_DAO,
                            private val LOGIN_LOCAL_DAO: LOGIN_LOCAL_DAO,
                            private val SESSION_DAO: SESSION_DAO,
                            private val database: WFNR_Room_Database) {

    val get_all_Question: LiveData<List<Topic_Question>> = TOPIC_QUESTION_DAO.getAll_Topic_Question()
    val get_All_Session: LiveData<List<Session>> = SESSION_DAO.get_Session()
    val getAll_Topics: LiveData<List<Topic_Notes>> = TOPIC_NOTE_DAO.getAll_Topic_Notes()
    val getAll_Day_Room: LiveData<List<Get_Day_Room>> = GET_DAY_ROOM_DAO.getAll_Day_Room()
    val getAll_Itinerary: LiveData<List<Topic_Itinerary>> = TOPIC_ITINERARY_DAO.getAll_Itinerary()

    val getAll_Session:LiveData<List<Get_Session>> = GET_SESSION_DAO.getAll_Session()
    val getAll_member:LiveData<List<LOGIN_LOCAL>> = LOGIN_LOCAL_DAO.getAll_Login_Local()

    val getAll_FilterData:LiveData<List<Get_Filter_Data_Local>> = GET_FILTER_DATA_LOCAL_DAO.getGET_FILTER_DATA_LOCAL()



    suspend fun insert_Day_room_list(day_room_List: List<Get_Day_Room>) {
        database.performDatabaseOperation {
            GET_DAY_ROOM_DAO.insert_Day_room_list(day_room_List)
        }
    }

    suspend fun delete_All_Get_Day_Room() {
        GET_DAY_ROOM_DAO.delete_All_Get_Day_Room()
    }

    suspend fun delete_All_Session() {
        GET_SESSION_DAO.delete_All_Session()
    }

    suspend fun insert_session_list(session_List: List<Get_Session>) {
        database.performDatabaseOperation {
            GET_SESSION_DAO.insert_Session_list(session_List)
        }
    }

    suspend fun insert_filter_list(filterDataList: MutableList<Get_Filter_Data_Local>) {

        database.performDatabaseOperation {
            GET_FILTER_DATA_LOCAL_DAO.insert_GET_FILTER_DATA_LOCAL_list(filterDataList)
        }

    }

   suspend fun delete_All_filter_data() {
        GET_FILTER_DATA_LOCAL_DAO.delete_All_GET_FILTER_DATA_LOCAL()
    }

    fun getFilteredData(query: String): LiveData<List<Get_Filter_Data_Local>> {
        return GET_FILTER_DATA_LOCAL_DAO.getFilteredData(query)
    }

    suspend fun saveRegistrationDataLocal(registrationDataLocalRequest: LOGIN_LOCAL) {

        database.performDatabaseOperation {
            LOGIN_LOCAL_DAO.insert_Login_Local(registrationDataLocalRequest)
        }

    }

    suspend fun storeFeedBack(topicFeedback: Topic_FeedBack) {

        database.performDatabaseOperation {
            TOPIC_FEEDBACK_DAO.insert_Topic_FeedBack(topicFeedback)
        }

    }

    suspend fun storeQuestionLocal(topicQuestion: Topic_Question) {

        database.performDatabaseOperation {
            TOPIC_QUESTION_DAO.insert_Topic_Question(topicQuestion)
        }

    }

    suspend fun Insert_Itinerary_Local(topicItinerary: Topic_Itinerary) {

        database.performDatabaseOperation {
            TOPIC_ITINERARY_DAO.insert_Topic_Itinerary(topicItinerary)
        }

    }

    suspend fun Delete_Itinerary_Local(id1: String) {

        database.performDatabaseOperation {
            TOPIC_ITINERARY_DAO.deleteByTopicId(id1)
        }

    }



     suspend fun insertTopicNote(topicNoteRequest: Topic_Notes) {

         return database.performDatabaseOperation {
             TOPIC_NOTE_DAO.insert_Topic_Notes(topicNoteRequest)
         }

     }

    suspend fun deleteData3(date: String) {
        return database.performDatabaseOperation {

            GET_SESSION_DAO.deleteSessionsByDate(date)
        }



    }

    fun getTopicItineraryByMemberID(memberId: String): LiveData<List<Topic_Itinerary>> {
        return TOPIC_ITINERARY_DAO.getTopic_ItineraryByMemberID(memberId)
    }

    fun getTopicQuestionsByMemberID(memberId: String): LiveData<List<Topic_Question>>? {
        return TOPIC_QUESTION_DAO.getTopic_QuestionByMemberID(memberId)

    }

    fun getTopicNotesByMemberID(memberId: String?): LiveData<List<Topic_Notes>>? {
        return TOPIC_NOTE_DAO.getTopic_NotesByMemberID(memberId!!)

    }

    fun checkCredentialsExist(wcnrRegNo: String, userEmailId: String): LiveData<Boolean> {
        return LOGIN_LOCAL_DAO.checkCredentialsExist(wcnrRegNo, userEmailId)
    }

    suspend fun delete_All_member() {
        LOGIN_LOCAL_DAO.delete_All_Login_Local()
    }

   suspend fun Insert_ItineraryList_Local(topicItineraryList: MutableList<Topic_Itinerary>) {

        database.performDatabaseOperation {
            TOPIC_ITINERARY_DAO.insert_Topic_Itinerary_list(topicItineraryList)
        }

    }

    suspend fun deleteAllItineraryData() {
        TOPIC_ITINERARY_DAO.delete_All_Topic_Itinerary()
    }

    suspend fun deleteAllNotesData() {
        TOPIC_NOTE_DAO.delete_All_Topic_Notes()
    }

    suspend fun Insert_Notes_Local(topicItineraryList: MutableList<Topic_Notes>) {

        database.performDatabaseOperation {
            TOPIC_NOTE_DAO.insert_Topic_Notes_list(topicItineraryList)
        }

    }

    suspend fun getEarliestItinerary(): Topic_Itinerary?
    {
        return TOPIC_ITINERARY_DAO.getEarliestItinerary()
    }

  /*  suspend fun getLoginLocalByCredentials(wcnrRegNo:String,user_email_id:String): LiveData<LOGIN_LOCAL?>
    {
        return LOGIN_LOCAL_DAO.getLoginLocalByCredentials(wcnrRegNo,user_email_id)
    }*/

   /* suspend fun getLoginLocalByCredentials(wcnrRegNo: String, userEmailId: String): LiveData<LOGIN_LOCAL?> {
        return LOGIN_LOCAL_DAO.getLoginLocalByCredentials(wcnrRegNo, userEmailId)
    }*/

    suspend fun getLoginLocalByCredentials(wcnrRegNo: String, userEmailId: String): LiveData<LOGIN_LOCAL?> {
        return LOGIN_LOCAL_DAO.getLoginLocalByCredentials(wcnrRegNo, userEmailId)
    }

   /* fun getSessionsByDate(selectedDate: String): LiveData<List<Get_Session>> {
        return GET_SESSION_DAO.getSessionsByDate(selectedDate)
    }*/
    fun getSessionsByDate(selectedDate: String): LiveData<List<Session>> {
        return SESSION_DAO.get_Sessions_ByDate(selectedDate)
    }

    fun getLoginLocalByEmail(emailId: String): LOGIN_LOCAL? {
        return LOGIN_LOCAL_DAO.getLoginLocalByEmail(emailId)
    }

    suspend fun getRecordsBeforeDateTime(sqlDateTime: String): List<Topic_Itinerary> {
        return TOPIC_ITINERARY_DAO.getRecordsBeforeDateTime(sqlDateTime)
    }

    suspend fun insertSession(sessions: List<Session>) {

        database.performDatabaseOperation {
            SESSION_DAO.insert_Session(sessions)

        }



    }

    suspend fun delete_Session() {
        SESSION_DAO.delete_All_Session()
    }

    suspend fun Insert_Question_Local(questionMutableList: MutableList<Topic_Question>?) {

        database.performDatabaseOperation {
            TOPIC_QUESTION_DAO.insert_Topic_Question_list(questionMutableList!!)
        }

    }

    suspend fun delete_All_Question() {
        TOPIC_QUESTION_DAO.delete_All_Topic_Question()
    }


}





