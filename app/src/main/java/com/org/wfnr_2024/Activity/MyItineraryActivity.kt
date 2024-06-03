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
import com.org.wfnr_2024.Adapter.MyItineraryAdapter
import com.org.wfnr_2024.Model.GET_FAVOURITE.Get_Favourite_Response
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
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
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database

class MyItineraryActivity:AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    lateinit var Event_backbtn:ImageView

    lateinit var RecyclerView_Itinerary:RecyclerView

    var member_id:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_itinerary)

        RecyclerView_Itinerary=findViewById(R.id.RecyclerView_Itinerary)
        Event_backbtn=findViewById(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener(this)

        getViewModel()
        createRoomDatabase()

        if (ConstantsApp.checkInternetConenction(applicationContext))
        {
            /*viewModel1.deleteAllItineraryData()
            getDataFromServer()*/

            getMemberID()
        }
        else
        {
            getMemberID()
            Toast.makeText(applicationContext, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            //get_All_Itinerary_FromLocal()
        }






    }

    private fun getDataFromServer() {
        viewModel.getItineraryFromServer(progressDialog)
        GetItineraryResponse()
    }

    private fun GetItineraryResponse() {
        viewModel.getFavouriteLiveData.observe(this, Observer {

            response->
            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()


                    try {

                        val login_member_id=sessionManager.getMember_ID()



                        val responseData: Get_Favourite_Response? = response.data

                        val ItineraryData=responseData!!.data

                        val topicItineraryList: MutableList<Topic_Itinerary> = mutableListOf()


                        for (data in ItineraryData)

                        {
                            if(login_member_id.equals(data.member_id))
                            {
                                Log.d(ConstantsApp.TAG,""+data.title)
                                val date=data.start_date
                                val begin=data.start_time
                                val end=data.end_time
                                val topic_name=data.title
                                val speaker_name=data.speaker
                                val topic_itinerary=""
                                val wcnr_id=data.member_id
                                val wcnr_name=data.fname+" "+data.lname
                                val wcnr_email=data.email
                                val inserted_date=""
                                val topic_id=data.topic_id
                                val is_fav=data.is_fav
                                val room_name=data.hall


                                val data=Topic_Itinerary(0,date,begin,end,topic_name,speaker_name,topic_itinerary,wcnr_id,
                                    wcnr_name,wcnr_email,inserted_date,topic_id,is_fav.toString(),room_name,"","","",
                                    "","","")

                                topicItineraryList.add(data)

                            }
                        }

                        if (topicItineraryList.size>0)
                        {
                            viewModel1.Insert_ItineraryList_Local(topicItineraryList)

                            getMemberID()
                        }
                        else
                        {
                            getMemberID()
                        }


                        //get_All_Itinerary_FromLocal()


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

        viewModel1.setMemberId(sessionManager.getMember_ID()!!)
        // viewModel1.setMemberId("43")
        get_All_Itinerary_FromLocal()

    }

    private fun get_All_Itinerary_FromLocal() {
        viewModel1.topicItineraries.observe(this, Observer { itineraries ->
            itineraries?.let { /*adapter.submitList(it)*/

          

                val size=itineraries.size

                if (size>0)
                {
                    setRecycleview(itineraries.distinctBy { it.topic_name })
                }
                else
                {
                    get_All_Itinerary_FromLocal()
                }



            }
        })
    }

    private fun setRecycleview(itineraries: List<Topic_Itinerary>) {
        RecyclerView_Itinerary.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MyItineraryAdapter(this,itineraries)
        RecyclerView_Itinerary.adapter = adapter
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