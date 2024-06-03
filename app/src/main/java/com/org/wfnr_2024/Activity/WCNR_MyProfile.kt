package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.SessionManager
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

class WCNR_MyProfile : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    lateinit var Dashboard_back:ImageView

    var member_id:String?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wcnr_my_profile)

        Dashboard_back=findViewById(R.id.Dashboard_back)

        Dashboard_back.setOnClickListener(this)


        getViewModel()
        createRoomDatabase()

        getMemberID()





    }

    private fun getMemberID() {
        viewModel1.getAll_Member.observe(this, Observer {
                response->

            var member_data=response

            val email=sessionManager.getUserEmail()

            for (member in member_data)
            {
                if (email.equals(member.user_email_id))
                {
                    member_id=member.member_id
                    Log.d(ConstantsApp.TAG,"member_id=>"+member_id)

                    // Retrieve user data from intent extras
                    val userId = intent.getIntExtra("userId", 0)
                    val userName = intent.getStringExtra("userName")
                    val userEmail = intent.getStringExtra("userEmail")
                    val userMobile = intent.getStringExtra("userMobile")
                    val userRegNo = intent.getStringExtra("userRegNo")
                    val userCity = intent.getStringExtra("userCity")
                    val userCountry = intent.getStringExtra("userCountry")
                    val userRegType = intent.getStringExtra("userRegType")

                    // Display user data in TextViews
                    findViewById<TextView>(R.id.textViewUserName).text = "User ID: ${member.member_id}"
                    findViewById<TextView>(R.id.textViewUserName).text = "${member.user_name}"
                    findViewById<TextView>(R.id.textViewUserregno).text = "WCNR Registration No: ${member.wcnr_reg_no}"
                    findViewById<TextView>(R.id.textViewUserEmail).text = "Email: ${member.user_email_id}"
                    findViewById<TextView>(R.id.textViewUserCity).text = "City: ${member.user_city}"
                    findViewById<TextView>(R.id.textViewUserCountry).text = "Country: ${member.user_country}"



                }

            }

        })
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

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MyWCNRSection::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Dashboard_back->
            {
                val intent = Intent(this, MyWCNRSection::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
