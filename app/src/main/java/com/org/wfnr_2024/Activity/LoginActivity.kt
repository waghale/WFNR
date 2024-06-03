package com.org.wfnr_2024.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.MyValidator
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.Utils.SessionManager_Login
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database

class LoginActivity:AppCompatActivity(), View.OnClickListener {

    lateinit var editTextMemberNo:EditText

    lateinit var editTextEmailOrPhone:EditText
    lateinit var loginButton:TextView
    lateinit var TextView_register:TextView
    lateinit var Dashboard_back:ImageView

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var sessionManager_Login:SessionManager_Login
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        getViewModel()
        createRoomDatabase()

        editTextMemberNo=findViewById(R.id.editTextMemberNo)
        editTextEmailOrPhone=findViewById(R.id.editTextEmailOrPhone)
        loginButton=findViewById(R.id.loginButton)
        TextView_register=findViewById(R.id.TextView_register)
        Dashboard_back=findViewById(R.id.Dashboard_back)

        Dashboard_back.setOnClickListener(this)

        // Text to be displayed
        val text = "New User? Register Here"

        // Create a SpannableString from the text
        val spannableString = SpannableString(text)

        // Find the start and end index of the text to be colored
        val startIndex = text.indexOf("Register Here")
        if (startIndex != -1) {
            val endIndex = startIndex + "Register Here".length

            // Apply the color span to the "Register Here" part
            spannableString.setSpan(
                ForegroundColorSpan(Color.BLUE),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Set the spannable string to the TextView
        TextView_register.text = spannableString



        loginButton.setOnClickListener(this)
        TextView_register.setOnClickListener(this)



    }

    private fun createRoomDatabase() {
        val database = WFNR_Room_Database.getDatabase(this)

        val GET_DAY_ROOM_DAO : GET_DAY_ROOM_DAO = database.GET_DAY_ROOM_DAO()
        val GET_SESSION_DAO: GET_SESSION_DAO=database.GET_SESSION_DAO()
        val GET_FILTER_DATA_LOCAL_DAO: GET_FILTER_DATA_LOCAL_DAO =database.GET_FILTER_DATA_LOCAL_DAO()
        val TOPIC_NOTE_DAO= database.TOPIC_NOTE_DAO()
        val TOPIC_QUESTION_DAO= database.TOPIC_QUESTION_DAO()
        val TOPIC_FEEDBACK_DAO= database. TOPIC_FEEDBACK_DAO()
        val TOPIC_ITINERARY_DAO= database.TOPIC_ITINERARY_DAO()
        val LOGIN_LOCAL_DAO= database.LOGIN_LOCAL_DAO()
        val SESSION_DAO= database.SESSION_DAO()

        val repository = WFNR_LOCAL_Repository(GET_DAY_ROOM_DAO,GET_SESSION_DAO,GET_FILTER_DATA_LOCAL_DAO,TOPIC_NOTE_DAO,TOPIC_QUESTION_DAO,TOPIC_FEEDBACK_DAO,TOPIC_ITINERARY_DAO,LOGIN_LOCAL_DAO,SESSION_DAO, database)

        viewModel1 = ViewModelProvider(this, WFNR_LOCAL_ViewModelFactory(repository)).get(WFNR_LOCAL_ViewModel::class.java)
    }

    private fun getViewModel() {
        val WFNRRespository= WFNRRespository()
        val WFNRProviderFactory= WFNRProviderFactory(WFNRRespository,application)
        viewModel= ViewModelProvider(this,WFNRProviderFactory).get(WFNR_ViewModel::class.java)

        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage(getString(R.string.please_wait))
        }

        sessionManager= SessionManager(this)
        sessionManager_Login= SessionManager_Login(this)



    }


    private fun validation():Boolean {
        var flag = true

        if (!MyValidator.isValidField(editTextMemberNo)) {
            flag = false
        }

        if (!MyValidator.isValidField(editTextEmailOrPhone)) {
            flag = false
        }

        /* if (!MyValidator.isValidField(binding.editTextMobile!!)) {
             flag = false
         }
         if (!MyValidator.isValidField(binding.editTextCollege!!)) {
             flag = false
         }*/


        return flag
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.loginButton->
            {

                if (validation())
                {
                    viewModel1.checkCredentialsExist(editTextMemberNo.text.toString(),
                        editTextEmailOrPhone.text.toString())
                    checkCredentialsExistResponse()

                }



            }
            R.id.TextView_register->
            {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.Dashboard_back->
            {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun checkCredentialsExistResponse() {
        viewModel1.credentialsLiveData.observe(this, Observer {
            response->
            Log.d(ConstantsApp.TAG,"Login response=>"+response)
            when(response.success)
            {
                true->
                {
                    viewModel1.getLoginLocalByCredentials(response.member_id!!,response.email!!).observe(this,
                        Observer { response->
                            sessionManager.setLogin("1")
                            sessionManager.setUserEmaiID(response!!.user_email_id!!)
                            sessionManager.setMemberId(response.member_id!!)
                            sessionManager_Login.setKeepLogin(true)

                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()

                        })



                }
                false->
                {

                }
            }
        })
    }


}