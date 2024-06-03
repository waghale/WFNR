package com.org.wfnr_2024.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.MyNotesAdapter
import com.org.wfnr_2024.Model.GET_FAVOURITE.Get_Favourite_Response
import com.org.wfnr_2024.Model.GET_NOTES.Get_Notes_Response
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
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database

class MyNotesActivity:AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    lateinit var RecyclerView_notes: RecyclerView

    lateinit var Event_backbtn: ImageView



    var member_id:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        RecyclerView_notes=findViewById(R.id.RecyclerView_notes)
        Event_backbtn=findViewById(R.id.Event_backbtn)
        Event_backbtn.setOnClickListener(this)

        getViewModel()
        createRoomDatabase()





        if (ConstantsApp.checkInternetConenction(applicationContext))
        {
            /*viewModel1.deleteAllNotesData()
            getDataFromServer()*/

            viewModel1.setMemberId(sessionManager.getMember_ID()!!)
            get_All_Notes_FromLocal()
        }
        else
        {
            viewModel1.setMemberId(sessionManager.getMember_ID()!!)
            get_All_Notes_FromLocal()
        }


    }

    private fun getDataFromServer() {
        viewModel.getNotesFromServer(progressDialog)
        GetNotesResponse()
    }

    private fun GetNotesResponse() {
        viewModel.getNotesLiveData.observe(this, Observer {

                response->
            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()


                    try {

                        val login_member_id=sessionManager.getMember_ID()



                        val responseData: Get_Notes_Response? = response.data

                        val ItineraryData=responseData!!.data

                        val topicItineraryList: MutableList<Topic_Notes> = mutableListOf()


                        for (data in ItineraryData)

                        {
                            if(login_member_id!!.equals(data.id))
                            {

                                val date=data.start_date
                                val begin=data.start_time
                                val end=data.end_time
                                val topic_name=data.topic
                                val speaker_name=data.speaker
                                val topic_itinerary=""
                                val wcnr_id=data.id
                                val wcnr_name=""
                                val wcnr_email=""
                                val inserted_date=""
                                val topic_id=data.topic_id


                                val data= Topic_Notes(0,date,begin,end,topic_name,speaker_name,topic_itinerary,wcnr_id.toString(),
                                    wcnr_name,wcnr_email,inserted_date)

                                topicItineraryList.add(data)

                            }
                        }

                        viewModel1.Insert_Notes_Local(topicItineraryList)

                        getMemberID()
                        get_All_Notes_FromLocal()


                    }

                    catch (e:Exception)
                    {
                        e.printStackTrace()
                    }









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

                    viewModel1.setNotesMemberId(member_id!!)
                    get_All_Notes_FromLocal()

                }

            }

        })
    }

    private fun get_All_Notes_FromLocal() {
        viewModel1.topicNotes.observe(this, Observer { notes ->
            notes?.let { /*adapter.submitList(it)*/

                Log.d(ConstantsApp.TAG,"notes=>"+notes)

                setRecycleview(notes)

            }
        })
    }

    private fun setRecycleview(itineraries: List<Topic_Notes>) {
        RecyclerView_notes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MyNotesAdapter(this,itineraries)
        RecyclerView_notes.adapter = adapter
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