package com.org.wfnr_2024.Utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session


class SessionManager() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var mContext: Context
    private val privateMode = 0


    companion object{
        private val PREF_NAME="WFNR"

         val KEY_IS_DATA_FETCHED = "isDataFetched"
        val KEY_IS_DATA_FETCHED_Session2 = "isDataFetched_session_2"
        val KEY_IS_DATA_FETCHED_Main = "isDataFetched_Main"

        val MY_TOKEN="My_Token"
        val USER_TYPE="User_Type"
        val MY_LOGIN="My_Login"
        val USER_EMAIL="USER_EMAIL"
        val MY_LOGIN_DATA="My_Login_Data"
        val MY_LOGIN_STUDENT_DATA="My_Login_Student_Data"
        val NOTIFICATION="My_Notification"
        val QUICK_LINKS="QUICK_LINKS"
        val EVENT_DATA="EVENT_DATA"
        val NOTIFICATION_BACK="NOTIFICATION_BACK"
        val MEMBER_DIRECTORY_DATA="MEMBER_DIRECTORY_DATA"
        val BOTTOM_MENU_BAR="BOTTOM_MENU_BAR"
        val MEMBER_NAME="MEMBER_NAME"
        val PATIENT_DATA="PATIENT_DATA"

        val KEY_LOGIN_DATA="KEY_LOGIN_DATA"
        val REFRACTIVE_ERROR_DATA="REFRACTIVE_ERROR_DATA"
        val PATIENT_IDENTITY="PATIENT_IDENTITY"
        val CAMP_USER_ID="CAMP_USER_ID"
        val SESSION="session"
        val BEARER_TOKEN="BEARER_TOKEN"
        val MEMBER_ID="MEMBER_ID"
    }

    constructor(context: Context):this()
    {
        mContext=context
        sharedPreferences=context.getSharedPreferences(PREF_NAME,privateMode)
        editor=sharedPreferences.edit()
    }

    fun setTokenNumber(token:String)
    {
        editor.putString(MY_TOKEN,token)
        editor.apply()
    }

    fun setNotification(notification:String)
    {
        editor.putString(NOTIFICATION,notification)
        editor.apply()
    }

    fun setBottomMenuBar(notification:String)
    {
        editor.putString(BOTTOM_MENU_BAR,notification)
        editor.apply()
    }

    fun setMemberName(member_name:String)
    {
        editor.putString(MEMBER_NAME,member_name)
        editor.apply()
    }

    fun getMemberName():String?
    {
        return sharedPreferences.getString(MEMBER_NAME,null)
    }
    fun getBottomMenuBar():String?
    {
        return sharedPreferences.getString(BOTTOM_MENU_BAR,null)
    }

    fun getNotification():String?
    {
        return sharedPreferences.getString(NOTIFICATION,null)
    }
    fun getTokenNumber():String?
    {
        return sharedPreferences.getString(MY_TOKEN,null)
    }

    fun setLogin(login:String)
    {
        editor.putString(MY_LOGIN,login)
        editor.apply()
    }
    fun setUserEmaiID(login:String)
    {
        editor.putString(USER_EMAIL,login)
        editor.apply()
    }
    fun setMemberId(login:String)
    {
        editor.putString(MEMBER_ID,login)
        editor.apply()
    }


    fun setNotificationBack(login:String)
    {
        editor.putString(NOTIFICATION_BACK,login)
        editor.apply()
    }

    fun getNotificationBack():String?
    {
        return sharedPreferences.getString(NOTIFICATION_BACK,null)
    }
    fun getLogin():String?
    {
        return sharedPreferences.getString(MY_LOGIN,null)
    }
    fun getUserEmail():String?
    {
        return sharedPreferences.getString(USER_EMAIL,null)
    }
    fun getMember_ID():String?
    {
        return sharedPreferences.getString(MEMBER_ID,null)
    }

    var isDataFetched: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_DATA_FETCHED, false)
        set(value) {
            editor.putBoolean(KEY_IS_DATA_FETCHED, value)
            editor.apply()
        }

    var isDataFetched_session2: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_DATA_FETCHED_Session2, false)
        set(value) {
            editor.putBoolean(KEY_IS_DATA_FETCHED_Session2, value)
            editor.apply()
        }

    var isDataFetched_Main: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_DATA_FETCHED_Main, false)
        set(value) {
            editor.putBoolean(KEY_IS_DATA_FETCHED_Main, value)
            editor.apply()
        }

   /* fun setLoginMemberData(data: List<LoginResponse>)
    {
        val gson = Gson()
        val userDataJson = gson.toJson(data)
        editor.putString("My_Login_Data", userDataJson)
        editor.apply()
    }*/

/*    fun getLoginMemberData(): List<LoginResponse.Data>? {
        val userDataJson = sharedPreferences.getString(MY_LOGIN_DATA, null)

        if (userDataJson != null) {
            val gson = Gson()
            val dataType = object : TypeToken<List<LoginResponse.Data>>() {}.type
            return gson.fromJson(userDataJson, dataType)
        }

        return null
    }*/

  /*  fun setLoginStudentData(data:StudentLoginResponse.User)
    {
        val gson = Gson()
        val userDataJson = gson.toJson(data)
        editor.putString(MY_LOGIN_STUDENT_DATA, userDataJson)
        editor.apply()
    }*/

  /*  fun setEventData(data: EventResponse.Data)
    {
        val gson = Gson()
        val userDataJson = gson.toJson(data)
        editor.putString(EVENT_DATA, userDataJson)
        editor.apply()
    }*/
  /*  fun getLoginStudentData():StudentLoginResponse.User?
    {
        val userDataJson = sharedPreferences.getString(MY_LOGIN_STUDENT_DATA, null)

        *//* if (userDataJson != null) {
             val gson = Gson()
             val dataType = object : TypeToken<StudentLoginResponse.User>() {}.type
             return gson.fromJson(userDataJson, dataType)
         }*//*

        if (userDataJson != null) {
            val gson = Gson()
            try {
                val dataType = object : TypeToken<StudentLoginResponse.User>() {}.type
                return gson.fromJson(userDataJson, dataType)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        return null
    }*/

/*    fun getEventData():EventResponse.Data?
    {
        val userDataJson = sharedPreferences.getString(EVENT_DATA, null)

       *//* if (userDataJson != null) {
            val gson = Gson()
            val dataType = object : TypeToken<StudentLoginResponse.User>() {}.type
            return gson.fromJson(userDataJson, dataType)
        }*//*

        if (userDataJson != null) {
            val gson = Gson()
            try {
                val dataType = object : TypeToken<EventResponse.Data>() {}.type
                return gson.fromJson(userDataJson, dataType)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        return null
    }*/

    fun setUserType(user:String?)
    {
        editor.putString(USER_TYPE,user)
        editor.apply()
    }

    fun getUserType():String?
    {
        return sharedPreferences.getString(USER_TYPE,null)
    }

    fun setQuickLinksURL(url:String?)
    {
        editor.putString(QUICK_LINKS,url)
        editor.apply()
    }

    fun getQuickLinksURL():String?
    {
        return sharedPreferences.getString(QUICK_LINKS,null)
    }



    /*@SuppressLint("SuspiciousIndentation")
    fun getLoginMemberData(): List<LoginResponse.Data>?
    {
        if (MY_LOGIN_DATA == null) {
            return null
        }

        val gson = Gson()
        val loginResponse = gson.fromJson(MY_LOGIN_DATA, LoginResponse::class.java)

        // Assuming "data" is a field in the LoginResponse class
        return loginResponse.data
    }
*/

  /*  fun getLoginMemberData(): List<LoginResponse.Data>? {
        if (MY_LOGIN_DATA == null) {
            return null
        }

        val gson = Gson()

        // Use TypeToken to specify that you're expecting a List<LoginResponse.Data>
        val type = object : TypeToken<LoginResponse>() {}.type
        val loginResponse = gson.fromJson<LoginResponse>(MY_LOGIN_DATA, type)

        // Assuming "data" is a field in the LoginResponse class
        return loginResponse.data
    }*/

    fun clearCache()
    {
        editor.clear()
        editor.commit()
    }

   /* fun saveLoginData(loginData: List<LoginData>) {
        val loginDataJson = Gson().toJson(loginData)
        editor.putString(KEY_LOGIN_DATA, loginDataJson)
        editor.apply()
    }

    fun saveRefractiveError(refractiveError: RefractiveError) {
        val refractiveErrorDataJson = Gson().toJson(refractiveError)
        val editor = sharedPreferences.edit()
        editor.putString(REFRACTIVE_ERROR_DATA, refractiveErrorDataJson)
        editor.apply()
    }
    fun getRefractiveError(): RefractiveError? {
        val refractiveErrorDataJson = sharedPreferences.getString(REFRACTIVE_ERROR_DATA, null)

        // Check if the data exists in SharedPreferences
        if (refractiveErrorDataJson != null) {
            // Convert the JSON string to RefractiveError object using Gson
            return Gson().fromJson(refractiveErrorDataJson, RefractiveError::class.java)
        }

        return null
    }


    fun getLoginData(): List<LoginData>? {
        val loginDataJson = sharedPreferences.getString(KEY_LOGIN_DATA, null)
        return Gson().fromJson(loginDataJson, Array<LoginData>::class.java)?.toList()
    }

    fun setPatientData(patientData: String)
    {
        editor.putString(PATIENT_DATA,patientData)
        editor.apply()
    }
    fun getPatientData():String? {
        return sharedPreferences.getString(PATIENT_DATA, null)
    }

    fun setPatientIdentity(s: String, aadharNo: String) {
        val identityString = "$s|$aadharNo"
        val editor = sharedPreferences.edit()
        editor.putString(PATIENT_IDENTITY, identityString)
        editor.apply()
    }

    fun getPatientIdentity(): Pair<String?, String?> {
        val identityString = sharedPreferences.getString(PATIENT_IDENTITY, null)
        val (s, aadharNo) = identityString?.split("|")?.let {
            if (it.size == 2) {
                Pair(it[0] as String, it[1] as String)
            } else {
                Pair(null, null)
            }
        } ?: Pair(null, null)

        return Pair(s, aadharNo)
    }

    fun setCampUserID(camp_id: String, user_id: String) {
        val identityString = "$camp_id|$user_id"
        val editor = sharedPreferences.edit()
        editor.putString(CAMP_USER_ID, identityString)
        editor.apply()
    }

    fun getCampUserID(): Pair<String?, String?> {
        val identityString = sharedPreferences.getString(CAMP_USER_ID, null)
        val (camp_id, user_id) = identityString?.split("|")?.let {
            if (it.size == 2) {
                Pair(it[0] as String, it[1] as String)
            } else {
                Pair(null, null)
            }
        } ?: Pair(null, null)

        return Pair(camp_id, user_id)
    }
*/

    /* fun extractPatientAndLoginData(sessionManager: SessionManager): Triple<Int?, Int?, String?> {
         val decodedText = sessionManager.getPatientData()

         // Use Gson to parse the JSON string into PatientData
         val gson = Gson()
         val patientData = gson.fromJson(decodedText, PatientData::class.java)

         val loginData = sessionManager.getLoginData()

         var patientId: String? = null
         var campId: String? = null
         var userId: String? = null

         // Extract values from PatientData
         patientId = patientData?.patientId?.toString()

         // Extract values from LoginData list
         *//*  for (i in 0 until loginData?.size ?: 0) {
              val data = loginData?.get(i)
              data?.let {
                  campId = it.camp_id
                  userId = it.Userid.toString()
              }
              // Assuming you want values from the first element only
              break
          }*//*

        for (data in loginData.orEmpty()) {
            campId = data.camp_id.toString()
            userId = data.Userid.toString()
            // Assuming you want values from the first element only
            break
        }

        return Triple(patientId!!.toInt(), campId!!.toInt(), userId)
    }*/

    fun saveSession(session: Session) {
        val gson = Gson()
        val sessionString = gson.toJson(session)
        sharedPreferences.edit().putString(SESSION, sessionString).apply()
    }

    fun getSession(): Session? {
        val gson = Gson()
        val sessionString = sharedPreferences.getString(SESSION, null)
        return if (sessionString != null) {
            gson.fromJson(sessionString, Session::class.java)
        } else {
            null
        }
    }

    fun clearSession() {
        sharedPreferences.edit().remove(SESSION).apply()
    }

    fun saveBearerToken(bearerToken: String) {

        editor.putString(BEARER_TOKEN,bearerToken)
        editor.apply()

    }
    fun getBearerToken():String?
    {
        return sharedPreferences.getString(BEARER_TOKEN,null)
    }




}