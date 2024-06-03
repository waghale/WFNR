package com.org.wfnr_2024.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.MyQuestionAdapter
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.Model.GET_QUESTIONS.Get_Question_Request
import com.org.wfnr_2024.Model.GET_QUESTIONS.get_Question_member_wise
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
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database

class MyQuestionActivity:AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    lateinit var RecyclerView_question: RecyclerView
    lateinit var Event_backbtn: ImageView

    var member_id:String?=null
    var QuestionMutableList:MutableList<Topic_Question>?=mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_question)

        RecyclerView_question=findViewById(R.id.RecyclerView_question)
        Event_backbtn=findViewById(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener(this)

        getViewModel()
        createRoomDatabase()

        if (ConstantsApp.checkInternetConenction(this))
        {
            if (sessionManager.getLogin().equals("1"))
            {
                viewModel1.delete_All_Question()
                val  Get_Question_Request= Get_Question_Request(sessionManager.getMember_ID())
                viewModel.getMemberQuestion(Get_Question_Request,progressDialog)
                GetQuestionMemberWiseResponse()
            }
            else
            {

            }
        }
        else
        {
            //viewModel1.setMemberId(sessionManager.getMember_ID()!!)
            // get_All_Question_FromLocal()
            get_All_Question_FromLocal1()
        }









        //getMemberID()






      


    }

    private fun GetQuestionMemberWiseResponse() {
      viewModel.getQuestionMemberWiseLiveData.observe(this, Observer {
          response->



          when(response)
          {
              is ResourceApp.Success->
              {
                  try {

                      val responseData: ResourceApp<get_Question_member_wise>? =response
                      var speakerName=""
                      var topic_Name=""


                    /*  for (data in responseData!!.data!!.data)
                      {

                          val sessionId=data.session_id
                          val topicId=data.topic_id



                          getTopicSpeakerName(sessionId, topicId) { topicName, speakerNames ->
                              // Now you can use the retrieved topicName and speakerNames here
                              // For example, you can display them in your UI or perform further processing
                              Log.d(ConstantsApp.TAG, "Topic Name: $topicName")
                              Log.d(ConstantsApp.TAG, "Speaker Names: $speakerNames")
                              speakerName= speakerNames.toString()
                              topic_Name= topicName!!

                              Log.d(ConstantsApp.TAG, "Topic Name: $topicName")
                              Log.d(ConstantsApp.TAG, "Speaker Names: $speakerNames")
                              val speakerName = speakerNames.joinToString(", ")
                              val topicName = topicName ?: ""

                              val topicQuestionObj = Topic_Question(
                                  0, "", "", "", data.question,
                                  speakerName, "", "", "", "", "", sessionId,
                                  data.hall, topicName
                              )

                              // Add the Topic_Question to the list inside the callback
                              QuestionMutableList?.add(topicQuestionObj)
                          }

                        *//*  val Topic_Question=Topic_Question(0,"","","",topic_question,
                              speakerName,"","","","","",data.session_id,
                              data.hall,topic_Name
                          )*//*

                         // QuestionMutableList!!.add(Topic_Question)
                      }

                      viewModel1.insertQuestionLocal(QuestionMutableList)
                      InsertQuestionResponse()*/




                      // Assuming you have a variable to keep track of the number of completed callbacks
                      var completedCallbacksCount = 0

// Inside your loop where you call getTopicSpeakerName
                      for (data in responseData!!.data!!.data) {
                          val sessionId = data.session_id
                          val topicId = data.topic_id

                          getTopicSpeakerName(sessionId, topicId) { topicName, speakerNames ->
                              // Inside the callback of getTopicSpeakerName

                              // Increment the count of completed callbacks
                              completedCallbacksCount++

                              val speakerName = speakerNames.joinToString(", ")
                              val topicName = topicName ?: ""

                              val topicQuestionObj = Topic_Question(
                                  0, "", "", "", data.question,
                                  speakerName, "", "", "", "", "", sessionId,
                                  data.hall, topicName
                              )

                              // Add the Topic_Question to the list
                              QuestionMutableList?.add(topicQuestionObj)

                              // Check if all callbacks have completed
                              if (completedCallbacksCount == responseData.data!!.data.size) {
                                  // All callbacks have completed, now you can insert into the local database
                                  viewModel1.insertQuestionLocal(QuestionMutableList)
                                  InsertQuestionResponse()
                              }
                          }
                      }



                  }
                  catch (e:Exception)
                  {
                      e.printStackTrace()
                  }




              }
              is ResourceApp.Error->
              {

              }
              is ResourceApp.Loading->
              {

              }
          }
      })
    }

    private fun InsertQuestionResponse() {
        viewModel1._insertSuccessQuestion.observe(this, Observer {
            response->
             when(response)
             {
                 "Success"->
                 {
                    viewModel1.get_All_Questions.observe(this, Observer {
                        response->
                         Log.d(ConstantsApp.TAG,"get_All_Questions=>"+response)
                        setRecycleview(response)
                    })
                 }
             }
        })
    }

    private fun getTopicSpeakerName(sessionId: String, topicId: String) {

        viewModel1.get_All_Session.observe(this, Observer {
            response->


            for (data in response)
            {
                if (sessionId.contains(data.id!!))
                {

                    var program_point=data.program_points

                    for (data in program_point!!)
                    {
                        if (topicId.contains(data.id!!))
                        {
                           val topic_name=data.primaryTitle

                            var speakers=data.speakers
                            for (data in speakers!!)
                            {
                                if (topicId.contains(data.program_point_id!!))
                                {
                                    val speaker_name=data.person!!.title?:" "+data.person.firstName+" "+data.person.lastName

                                }


                                }

                        }

                    }



                }
            }
        })

    }

    private fun getTopicSpeakerName(sessionId: String, topicId: String, callback: (topicName: String?, speakerNames: List<String>) -> Unit) {
        viewModel1.get_All_Session.observe(this, Observer { response ->
            val session = response.firstOrNull { it.id == sessionId }
            session?.let { session ->
                val programPoint = session.program_points?.firstOrNull { it.id == topicId }
                programPoint?.let { programPoint ->
                    val topicName = programPoint.primaryTitle
                    val speakers = programPoint.speakers
                    val speakerNames = mutableListOf<String>()
                    speakers?.forEach { speaker ->
                        if (speaker.program_point_id == topicId) {
                            val speakerName = "${speaker.person?.title ?: ""} ${speaker.person?.firstName} ${speaker.person?.lastName}"
                            speakerNames.add(speakerName.trim())
                        }
                    }
                    // Pass the retrieved data to the callback function
                    callback(topicName, speakerNames)
                }
            }
        })
    }



    /*private fun getMemberID() {
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

                    viewModel1.setQuestionMemberId(member_id!!)
                    get_All_Question_FromLocal()

                }

            }

        })
    }*/

    private fun get_All_Question_FromLocal() {
        viewModel1.topicQuestion.observe(this, Observer { itineraries ->
            itineraries?.let { /*adapter.submitList(it)*/

                Log.d(ConstantsApp.TAG,"questions=>"+itineraries)

                setRecycleview(itineraries)

            }
        })
    }

    private fun get_All_Question_FromLocal1() {
        viewModel1.get_All_Questions.observe(this, Observer { itineraries ->
            itineraries?.let { /*adapter.submitList(it)*/

                Log.d(ConstantsApp.TAG,"questions=>"+itineraries)

                setRecycleview(itineraries)

            }
        })
    }

    private fun setRecycleview(itineraries: List<Topic_Question>) {
        RecyclerView_question.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MyQuestionAdapter(this,itineraries)
        RecyclerView_question.adapter = adapter
        adapter.notifyDataSetChanged()

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
            R.id.Event_backbtn->
            {
                val intent = Intent(this, MyWCNRSection::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}