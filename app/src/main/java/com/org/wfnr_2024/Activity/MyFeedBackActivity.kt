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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.FeedBackAdapter
import com.org.wfnr_2024.Interface.FeedBack_Callback
import com.org.wfnr_2024.Model.FEEDBACK.Send_FeedBack_To_Server_Request
import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_feedback
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.ViewModel.ResourceApp
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

class MyFeedBackActivity:AppCompatActivity(), FeedBack_Callback, View.OnClickListener {

     var FeedbackArrayList:ArrayList<Section_feedback>?=null

    private val feedbackList = mutableListOf<Section_feedback>()
    lateinit var RecyclerView_feedback:RecyclerView
    lateinit var TextView_FeedBack_Submit:TextView
    lateinit var Event_backbtn:ImageView
    lateinit var MenuAdapter: FeedBackAdapter
    var member_id:String?=null

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        getViewModel()
        createRoomDatabase()

        RecyclerView_feedback=findViewById(R.id.RecyclerView_feedback)
        TextView_FeedBack_Submit=findViewById(R.id.TextView_FeedBack_Submit)

        TextView_FeedBack_Submit.setOnClickListener(this)

        Event_backbtn=findViewById(R.id.Event_backbtn)
        Event_backbtn.setOnClickListener(this)

        FeedbackArrayList= ArrayList()

        FeedbackArrayList!!.add(Section_feedback("Relevance of Topics",0.0f,""))
        FeedbackArrayList!!.add(Section_feedback("Quality of Scientific Content",0.0f,""))
        FeedbackArrayList!!.add(Section_feedback("Speaker Profile",0.0f,""))
        FeedbackArrayList!!.add(Section_feedback("Venue",0.0f,""))
        FeedbackArrayList!!.add(Section_feedback("Audio Visual Arrangements",0.0f,""))
        FeedbackArrayList!!.add(Section_feedback("Comments",0.0f,""))

        updateRecyclerView(RecyclerView_feedback, FeedbackArrayList!!)
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
                }

            }

        })
    }

    private fun updateRecyclerView(recyclerView: RecyclerView, MenuArrayList: ArrayList<Section_feedback>) {

        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.visibility = View.VISIBLE
        // noTopicTextView.visibility = View.GONE

        // Initialize and set the adapter

        MenuAdapter = FeedBackAdapter(MenuArrayList,this,this)
        recyclerView.adapter = MenuAdapter


    }

    override fun FeedBack_rating(data: Section_feedback, position: Int) {
        Log.d(ConstantsApp.TAG,"rating=>"+data.rating)
        Log.d(ConstantsApp.TAG,"title=>"+data.title)

        val existingFeedback = feedbackList.find { it.title == data.title }
        if (existingFeedback != null) {

            if(data.title.equals("Comments"))
            {
                existingFeedback.rating = 0.0f
                existingFeedback.comments = data.comments
            }
            else
            {
                existingFeedback.rating = data.rating
                existingFeedback.comments =""
            }

        } else {
            feedbackList.add(data)
        }

        Log.d(ConstantsApp.TAG,"feedbackList=>"+feedbackList)



    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.TextView_FeedBack_Submit->
            {

                try {
                    val relevance = getFeedbackByTitle("Relevance of Topics")!!.rating.toString()
                    val quality = getFeedbackByTitle("Quality of Scientific Content")!!.rating.toString()
                    val speaker_profile = getFeedbackByTitle("Speaker Profile")!!.rating.toString()
                    val venue = getFeedbackByTitle("Venue")!!.rating.toString()
                    val arrangements = getFeedbackByTitle("Audio Visual Arrangements")!!.rating.toString()
                    val comments = getFeedbackByTitle("Comments")!!.comments.toString()


                    val data= Send_FeedBack_To_Server_Request(sessionManager.getMember_ID(),relevance,quality.toString(),
                        speaker_profile.toString(),venue.toString(),arrangements.toString(),comments,1)

                    Send_data_to_server_feedback(data)
                }
                catch (e:Exception)
                {
                       e.printStackTrace()
                }








            }

            R.id.Event_backbtn->
            {
                val intent = Intent(this, MyWCNRSection::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun Send_data_to_server_feedback(data: Send_FeedBack_To_Server_Request) {
        viewModel.Send_data_to_server_feedback(progressDialog,data)

        Send_data_to_server_feedback_Response()

    }

    private fun Send_data_to_server_feedback_Response() {
        viewModel.storeOverAllFeedbackLiveData.observe(this, Observer {
            response->
            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                  /*  val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()*/
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                      progressDialog.show()
                }
            }
        })
    }

    private fun getFeedbackByTitle(title: String): Section_feedback? {
        return feedbackList.find { it.title == title }
    }
    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MyWCNRSection::class.java)
        startActivity(intent)
        finish()
    }
}