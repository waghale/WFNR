package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Fragment.HomeFragment
import com.org.wfnr_2024.Fragment.MenuFragment
import com.org.wfnr_2024.Fragment.NotificationFragment
import com.org.wfnr_2024.Model.token.TokenResponse
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient1
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.org.wfnr_2024.CMPL_SERVER_MODEL.Sesssion_Model
import com.org.wfnr_2024.Fragment.SIGFragment
import com.org.wfnr_2024.JOBSERVICE.MyJobService2
import com.org.wfnr_2024.JOBSERVICE.MyJobService3
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_REQUEST
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE
import com.org.wfnr_2024.Model.GET_FILTER_DATA.GET_FILTER_REQUEST
import com.org.wfnr_2024.Model.GET_FILTER_DATA.GET_FILTER_RESPONSE
import com.org.wfnr_2024.Model.GET_MEMBER.Get_Member_Response
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_REQUEST
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.Model.ITINERARY.Send_Favourite_Request
import com.org.wfnr_2024.Model.Note.Topic_Note_Request
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.ViewModel.ResourceApp
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database
import kotlinx.coroutines.launch


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(){

    private lateinit var imageViewMenu: ImageView
    private var deviceToken: String? = null
    private val JOB_ID = 123
    lateinit var bottomNav : BottomNavigationView
    private var privacyPolicyDialog: AlertDialog? = null
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var appUpdateManager: AppUpdateManager

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager:SessionManager

    lateinit var progressDialog1: ProgressDialog
    lateinit var progressDialog2: ProgressDialog
    lateinit var progressDialog3: ProgressDialog





    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        getViewModel()
        createRoomDatabase()

       // scheduleJob()
       MyJobService3.scheduleJob(this)

        if (ConstantsApp.checkInternetConenction(this))
        {
            viewModel1.delete_session()
            viewModel1.delete_All_Get_Day_Room()
            viewModel1.delete_All_member()

            val dates = listOf("22-05-2024", "23-05-2024", "24-05-2024", "25-05-2024")

            getSessionData(dates)
            Get_Member_Data_From_Server()
            getDays()
        }












      /*  if (sessionManager.isDataFetched_Main==false)
        {
            if (ConstantsApp.checkInternetConenction(applicationContext))
            {



                viewModel1.delete_All_Get_Day_Room()
                viewModel1.delete_All_Session()
                viewModel1.delete_All_filter_data()
                viewModel1.delete_All_member()

                // Get_Member_Data_From_Server()


                //showDialog()


                lifecycleScope.launch {

                    *//*  get_All_ITINERARY()
                      get_All_Topic()*//*
                    Get_Member_Data_From_Server()
                    getDays()
                    getFilterData()

                    getSession4()
                    getSession1()



                   *//* get_All_ITINERARY()
                    get_All_Topic()*//*

                    sessionManager.isDataFetched_Main=true

                }

                var isDataFetched =sessionManager.isDataFetched
                Log.d(ConstantsApp.TAG,"isDataFetched=>"+isDataFetched)

                //fetchDataSequentially()




                *//* if (!sessionManager.isDataFetched) {
                     fetchDataSequentially()
                 }*//*
            }
            else
            {
                Toast.makeText(applicationContext, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        }*/




        sharedPreferences = getPreferences(Context.MODE_PRIVATE)






        appUpdateManager = AppUpdateManagerFactory.create(this)

        checkForAppUpdates()

        imageViewMenu = findViewById(R.id.Wcnrbtn)

        FirebaseApp.initializeApp(this)


        // Retrieve FCM token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Assign Firebase token to class-level property
                    deviceToken = task.result

                    Log.d("mytag", "FCM Token: $deviceToken")
                    // Handle the token (e.g., send it to your server)
                    deviceToken?.let { sendToken(it) }
                } else {
                    Log.e("mytag", "Failed to retrieve FCM token", task.exception)
                }
            }



        imageViewMenu.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }



    loadFragment(HomeFragment())
    bottomNav = findViewById(R.id.bottomNavigationn) as BottomNavigationView
    bottomNav.setOnItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                loadFragment(HomeFragment())
                true
            }
            R.id.message -> {


                loadFragment(NotificationFragment())
                true
            }
            R.id.settings -> {
                loadFragment(SIGFragment())
                true
            }
            R.id.menu -> {
                loadFragment(MenuFragment())
                true
            }

            else -> {
                  false
            }
        }
    }
}

    private fun getSessionData(dates: List<String>) {

        viewModel.getAllSessionsSequentially(dates)
        viewModel.sessions.observe(this, Observer { response ->
            when (response) {
                is ResourceApp.Success -> {
                    response.data?.let { sessionResponseList ->
                        // Handle the success state and update the UI with the session data
                        val allSessions = sessionResponseList.flatMap { it.sessions }
                       // sessionAdapter.submitList(allSessions)
                        Log.d(ConstantsApp.TAG,"allSessions=>"+allSessions)

                        val sessionList = mutableListOf<Session>()

                        for (session in allSessions) {
                            val session1 = Session(
                                _id = 0,
                                begin = session.begin ?: "",
                                chair = session.chair ?: "",
                                chairs = session.chairs.mapNotNull { chair ->
                                    chair.person?.let { person ->
                                        Session.Chair(
                                            created_at = chair.created_at ?: "",
                                            id = chair.id,
                                            person = Session.Chair.Person(
                                                city = person.city ?: "",
                                                company = person.company ?: "",
                                                country = person.country ?: "",
                                                created_at = person.created_at ?: "",
                                                department = person.department ?: "",
                                                displayValue = person.displayValue ?: "",
                                                firstName = person.firstName ?: "",
                                                gender = person.gender ?: "",
                                                id = person.id ?: "",
                                                lastName = person.lastName ?: "",
                                                state = person.state ?: "",
                                                street = person.street ?: "",
                                                title = person.title ?: "",
                                                updated_at = person.updated_at ?: "",
                                                zip = person.zip ?: ""
                                            ),
                                            person_id = chair.person_id ?: "",
                                            position = chair.position ?: 0,
                                            session_id = chair.session_id ?: "",
                                            updated_at = chair.updated_at ?: ""
                                        )
                                    }
                                },
                                created_at = session.created_at ?: "",
                                date = session.date ?: "",
                                description = session.description ?: "",
                                end = session.end ?: "",
                                id = session.id ?: "",
                                isEposterSession = session.isEposterSession,
                                program_points = session.program_points.map { programPoint ->
                                    Session.ProgramPoint(
                                        begin = programPoint.begin ?: "",
                                        created_at = programPoint.begin ?: "",
                                        discussionTime = programPoint.discussionTime,
                                        end = programPoint.end ?: "",
                                        id = programPoint.id ?: "",
                                        isEposter = programPoint.isEposter,
                                        position = programPoint.position,
                                        primaryTitle = programPoint.primaryTitle ?: "",
                                        secondaryTitle = programPoint.secondaryTitle ?: "",
                                        session_id = programPoint.session_id ?: "",
                                        speaker = programPoint.speaker ?: "",
                                        speakers = programPoint.speakers.mapNotNull { speaker ->
                                            speaker.person?.let { person ->
                                                Session.ProgramPoint.Speaker(
                                                    created_at = speaker.created_at ?: "",
                                                    id = speaker.id,
                                                    person = Session.ProgramPoint.Speaker.Person(
                                                        city = person.city ?: "",
                                                        company = person.company ?: "",
                                                        country = person.country ?: "",
                                                        created_at = person.created_at ?: "",
                                                        department = person.department ?: "",
                                                        displayValue = person.displayValue ?: "",
                                                        firstName = person.firstName ?: "",
                                                        gender = person.gender ?: "",
                                                        id = person.id ?: "",
                                                        lastName = person.lastName ?: "",
                                                        state = person.state ?: "",
                                                        street = person.street ?: "",
                                                        title = person.title ?: "",
                                                        updated_at = person.updated_at ?: "",
                                                        zip = person.zip ?: ""
                                                    ),
                                                    person_id = speaker.person_id ?: "",
                                                    position = speaker.position ?: "",
                                                    program_point_id = speaker.program_point_id ?: "",
                                                    updated_at = speaker.updated_at ?: ""
                                                )
                                            }
                                        },
                                        talkTime = programPoint.talkTime,
                                        updated_at = programPoint.updated_at ?: ""
                                    )
                                },
                                room = Session.Room(
                                    created_at = session.room.created_at ?: "",
                                    event = session.room.event ?: "",
                                    id = session.room.id ?: "",
                                    name = session.room.name ?: "",
                                    roomAddition = session.room.roomAddition ?: "",
                                    updated_at = session.room.updated_at ?: ""
                                ),
                                roomOrderPosition = session.roomOrderPosition,
                                room_id = session.room_id ?: "",
                                subtitle = session.subtitle ?: "",
                                timeslotTitle = session.timeslotTitle ?: "",
                                title = session.title ?: "",
                                type = Session.Type(
                                    color = session.type.color ?: "",
                                    colorBackground = session.type.colorBackground ?: "",
                                    colorBorder = session.type.colorBorder ?: "",
                                    colorFont = session.type.colorFont ?: "",
                                    colorHighlight = session.type.colorHighlight ?: "",
                                    created_at = session.type.created_at ?: "",
                                    description = session.type.description ?: "",
                                    id = session.type.id ?: "",
                                    name = session.type.name ?: "",
                                    updated_at = session.type.updated_at ?: ""
                                ),
                                type_id = session.type_id ?: "",
                                updated_at = session.updated_at ?: ""
                            )
                            sessionList.add(session1)
                        }


                        viewModel1.insertSession(sessionList)
                    }
                }
                is ResourceApp.Error -> {
                    // Handle the error state
                    //Toast.makeText(this, "Error: ${response.message}", Toast.LENGTH_LONG).show()
                }
                is ResourceApp.Loading -> {
                    // Handle the loading state
                    // Show a loading spinner or something
                }
            }
        })




    }


    private fun getSession_25() {
        val date="25-05-2024"
        viewModel.getSession22(progressDialog,date)
        getSession25Response()
    }
    private fun getSession25Response() {
        viewModel.getSessionLiveData.observe(this, Observer {
                response->

            when (response) {
                is ResourceApp.Success -> {
                    val responseData: ResourceApp<Sesssion_Model>? = response
                    val sessions = responseData!!.data!!.sessions
                    Log.d(ConstantsApp.TAG, "getSession22Response=>" + responseData!!.data!!.sessions)

                    val sessionList = mutableListOf<Session>()

                    for (session in sessions) {
                        val session1 = Session(
                            _id = 0,
                            begin = session.begin ?: "",
                            chair = session.chair ?: "",
                            chairs = session.chairs.mapNotNull { chair ->
                                chair.person?.let { person ->
                                    Session.Chair(
                                        created_at = chair.created_at ?: "",
                                        id = chair.id,
                                        person = Session.Chair.Person(
                                            city = person.city ?: "",
                                            company = person.company ?: "",
                                            country = person.country ?: "",
                                            created_at = person.created_at ?: "",
                                            department = person.department ?: "",
                                            displayValue = person.displayValue ?: "",
                                            firstName = person.firstName ?: "",
                                            gender = person.gender ?: "",
                                            id = person.id ?: "",
                                            lastName = person.lastName ?: "",
                                            state = person.state ?: "",
                                            street = person.street ?: "",
                                            title = person.title ?: "",
                                            updated_at = person.updated_at ?: "",
                                            zip = person.zip ?: ""
                                        ),
                                        person_id = chair.person_id ?: "",
                                        position = chair.position ?: 0,
                                        session_id = chair.session_id ?: "",
                                        updated_at = chair.updated_at ?: ""
                                    )
                                }
                            },
                            created_at = session.created_at ?: "",
                            date = session.date ?: "",
                            description = session.description ?: "",
                            end = session.end ?: "",
                            id = session.id ?: "",
                            isEposterSession = session.isEposterSession,
                            program_points = session.program_points.map { programPoint ->
                                Session.ProgramPoint(
                                    begin = programPoint.begin ?: "",
                                    created_at = programPoint.begin ?: "",
                                    discussionTime = programPoint.discussionTime,
                                    end = programPoint.end ?: "",
                                    id = programPoint.id ?: "",
                                    isEposter = programPoint.isEposter,
                                    position = programPoint.position,
                                    primaryTitle = programPoint.primaryTitle ?: "",
                                    secondaryTitle = programPoint.secondaryTitle ?: "",
                                    session_id = programPoint.session_id ?: "",
                                    speaker = programPoint.speaker ?: "",
                                    speakers = programPoint.speakers.mapNotNull { speaker ->
                                        speaker.person?.let { person ->
                                            Session.ProgramPoint.Speaker(
                                                created_at = speaker.created_at ?: "",
                                                id = speaker.id,
                                                person = Session.ProgramPoint.Speaker.Person(
                                                    city = person.city ?: "",
                                                    company = person.company ?: "",
                                                    country = person.country ?: "",
                                                    created_at = person.created_at ?: "",
                                                    department = person.department ?: "",
                                                    displayValue = person.displayValue ?: "",
                                                    firstName = person.firstName ?: "",
                                                    gender = person.gender ?: "",
                                                    id = person.id ?: "",
                                                    lastName = person.lastName ?: "",
                                                    state = person.state ?: "",
                                                    street = person.street ?: "",
                                                    title = person.title ?: "",
                                                    updated_at = person.updated_at ?: "",
                                                    zip = person.zip ?: ""
                                                ),
                                                person_id = speaker.person_id ?: "",
                                                position = speaker.position ?: "",
                                                program_point_id = speaker.program_point_id ?: "",
                                                updated_at = speaker.updated_at ?: ""
                                            )
                                        }
                                    },
                                    talkTime = programPoint.talkTime,
                                    updated_at = programPoint.updated_at ?: ""
                                )
                            },
                            room = Session.Room(
                                created_at = session.room.created_at ?: "",
                                event = session.room.event ?: "",
                                id = session.room.id ?: "",
                                name = session.room.name ?: "",
                                roomAddition = session.room.roomAddition ?: "",
                                updated_at = session.room.updated_at ?: ""
                            ),
                            roomOrderPosition = session.roomOrderPosition,
                            room_id = session.room_id ?: "",
                            subtitle = session.subtitle ?: "",
                            timeslotTitle = session.timeslotTitle ?: "",
                            title = session.title ?: "",
                            type = Session.Type(
                                color = session.type.color ?: "",
                                colorBackground = session.type.colorBackground ?: "",
                                colorBorder = session.type.colorBorder ?: "",
                                colorFont = session.type.colorFont ?: "",
                                colorHighlight = session.type.colorHighlight ?: "",
                                created_at = session.type.created_at ?: "",
                                description = session.type.description ?: "",
                                id = session.type.id ?: "",
                                name = session.type.name ?: "",
                                updated_at = session.type.updated_at ?: ""
                            ),
                            type_id = session.type_id ?: "",
                            updated_at = session.updated_at ?: ""
                        )
                        sessionList.add(session1)
                    }


                    viewModel1.insertSession(sessionList)

                    // Now you have the sessionList containing Session objects, you can use it as needed.
                }
                is ResourceApp.Error -> {
                    // Handle error case if necessary
                }
                is ResourceApp.Loading -> {
                    // Handle loading state if necessary
                }
            }




        })
    }

    private fun getSession_24() {
        val date="24-05-2024"
        viewModel.getSession22(progressDialog,date)
        getSession24Response()
    }
    private fun getSession24Response() {
        viewModel.getSessionLiveData.observe(this, Observer {
                response->

            when (response) {
                is ResourceApp.Success -> {
                    val responseData: ResourceApp<Sesssion_Model>? = response
                    val sessions = responseData!!.data!!.sessions
                    Log.d(ConstantsApp.TAG, "getSession22Response=>" + responseData!!.data!!.sessions)

                    val sessionList = mutableListOf<Session>()

                    for (session in sessions) {
                        val session1 = Session(
                            _id = 0,
                            begin = session.begin ?: "",
                            chair = session.chair ?: "",
                            chairs = session.chairs.mapNotNull { chair ->
                                chair.person?.let { person ->
                                    Session.Chair(
                                        created_at = chair.created_at ?: "",
                                        id = chair.id,
                                        person = Session.Chair.Person(
                                            city = person.city ?: "",
                                            company = person.company ?: "",
                                            country = person.country ?: "",
                                            created_at = person.created_at ?: "",
                                            department = person.department ?: "",
                                            displayValue = person.displayValue ?: "",
                                            firstName = person.firstName ?: "",
                                            gender = person.gender ?: "",
                                            id = person.id ?: "",
                                            lastName = person.lastName ?: "",
                                            state = person.state ?: "",
                                            street = person.street ?: "",
                                            title = person.title ?: "",
                                            updated_at = person.updated_at ?: "",
                                            zip = person.zip ?: ""
                                        ),
                                        person_id = chair.person_id ?: "",
                                        position = chair.position ?: 0,
                                        session_id = chair.session_id ?: "",
                                        updated_at = chair.updated_at ?: ""
                                    )
                                }
                            },
                            created_at = session.created_at ?: "",
                            date = session.date ?: "",
                            description = session.description ?: "",
                            end = session.end ?: "",
                            id = session.id ?: "",
                            isEposterSession = session.isEposterSession,
                            program_points = session.program_points.map { programPoint ->
                                Session.ProgramPoint(
                                    begin = programPoint.begin ?: "",
                                    created_at = programPoint.begin ?: "",
                                    discussionTime = programPoint.discussionTime,
                                    end = programPoint.end ?: "",
                                    id = programPoint.id ?: "",
                                    isEposter = programPoint.isEposter,
                                    position = programPoint.position,
                                    primaryTitle = programPoint.primaryTitle ?: "",
                                    secondaryTitle = programPoint.secondaryTitle ?: "",
                                    session_id = programPoint.session_id ?: "",
                                    speaker = programPoint.speaker ?: "",
                                    speakers = programPoint.speakers.mapNotNull { speaker ->
                                        speaker.person?.let { person ->
                                            Session.ProgramPoint.Speaker(
                                                created_at = speaker.created_at ?: "",
                                                id = speaker.id,
                                                person = Session.ProgramPoint.Speaker.Person(
                                                    city = person.city ?: "",
                                                    company = person.company ?: "",
                                                    country = person.country ?: "",
                                                    created_at = person.created_at ?: "",
                                                    department = person.department ?: "",
                                                    displayValue = person.displayValue ?: "",
                                                    firstName = person.firstName ?: "",
                                                    gender = person.gender ?: "",
                                                    id = person.id ?: "",
                                                    lastName = person.lastName ?: "",
                                                    state = person.state ?: "",
                                                    street = person.street ?: "",
                                                    title = person.title ?: "",
                                                    updated_at = person.updated_at ?: "",
                                                    zip = person.zip ?: ""
                                                ),
                                                person_id = speaker.person_id ?: "",
                                                position = speaker.position ?: "",
                                                program_point_id = speaker.program_point_id ?: "",
                                                updated_at = speaker.updated_at ?: ""
                                            )
                                        }
                                    },
                                    talkTime = programPoint.talkTime,
                                    updated_at = programPoint.updated_at ?: ""
                                )
                            },
                            room = Session.Room(
                                created_at = session.room.created_at ?: "",
                                event = session.room.event ?: "",
                                id = session.room.id ?: "",
                                name = session.room.name ?: "",
                                roomAddition = session.room.roomAddition ?: "",
                                updated_at = session.room.updated_at ?: ""
                            ),
                            roomOrderPosition = session.roomOrderPosition,
                            room_id = session.room_id ?: "",
                            subtitle = session.subtitle ?: "",
                            timeslotTitle = session.timeslotTitle ?: "",
                            title = session.title ?: "",
                            type = Session.Type(
                                color = session.type.color ?: "",
                                colorBackground = session.type.colorBackground ?: "",
                                colorBorder = session.type.colorBorder ?: "",
                                colorFont = session.type.colorFont ?: "",
                                colorHighlight = session.type.colorHighlight ?: "",
                                created_at = session.type.created_at ?: "",
                                description = session.type.description ?: "",
                                id = session.type.id ?: "",
                                name = session.type.name ?: "",
                                updated_at = session.type.updated_at ?: ""
                            ),
                            type_id = session.type_id ?: "",
                            updated_at = session.updated_at ?: ""
                        )
                        sessionList.add(session1)
                    }


                    viewModel1.insertSession(sessionList)

                    // Now you have the sessionList containing Session objects, you can use it as needed.
                }
                is ResourceApp.Error -> {
                    // Handle error case if necessary
                }
                is ResourceApp.Loading -> {
                    // Handle loading state if necessary
                }
            }




        })
    }

    private fun getSession_23() {
        val date="23-05-2024"
        viewModel.getSession22(progressDialog,date)
        getSession23Response()
    }
    private fun getSession23Response() {
        viewModel.getSessionLiveData.observe(this, Observer {
                response->

            when (response) {
                is ResourceApp.Success -> {
                    val responseData: ResourceApp<Sesssion_Model>? = response
                    val sessions = responseData!!.data!!.sessions
                    Log.d(ConstantsApp.TAG, "getSession22Response=>" + responseData!!.data!!.sessions)

                    val sessionList = mutableListOf<Session>()

                    for (session in sessions) {
                        val session1 = Session(
                            _id = 0,
                            begin = session.begin ?: "",
                            chair = session.chair ?: "",
                            chairs = session.chairs.mapNotNull { chair ->
                                chair.person?.let { person ->
                                    Session.Chair(
                                        created_at = chair.created_at ?: "",
                                        id = chair.id,
                                        person = Session.Chair.Person(
                                            city = person.city ?: "",
                                            company = person.company ?: "",
                                            country = person.country ?: "",
                                            created_at = person.created_at ?: "",
                                            department = person.department ?: "",
                                            displayValue = person.displayValue ?: "",
                                            firstName = person.firstName ?: "",
                                            gender = person.gender ?: "",
                                            id = person.id ?: "",
                                            lastName = person.lastName ?: "",
                                            state = person.state ?: "",
                                            street = person.street ?: "",
                                            title = person.title ?: "",
                                            updated_at = person.updated_at ?: "",
                                            zip = person.zip ?: ""
                                        ),
                                        person_id = chair.person_id ?: "",
                                        position = chair.position ?: 0,
                                        session_id = chair.session_id ?: "",
                                        updated_at = chair.updated_at ?: ""
                                    )
                                }
                            },
                            created_at = session.created_at ?: "",
                            date = session.date ?: "",
                            description = session.description ?: "",
                            end = session.end ?: "",
                            id = session.id ?: "",
                            isEposterSession = session.isEposterSession,
                            program_points = session.program_points.map { programPoint ->
                                Session.ProgramPoint(
                                    begin = programPoint.begin ?: "",
                                    created_at = programPoint.begin ?: "",
                                    discussionTime = programPoint.discussionTime,
                                    end = programPoint.end ?: "",
                                    id = programPoint.id ?: "",
                                    isEposter = programPoint.isEposter,
                                    position = programPoint.position,
                                    primaryTitle = programPoint.primaryTitle ?: "",
                                    secondaryTitle = programPoint.secondaryTitle ?: "",
                                    session_id = programPoint.session_id ?: "",
                                    speaker = programPoint.speaker ?: "",
                                    speakers = programPoint.speakers.mapNotNull { speaker ->
                                        speaker.person?.let { person ->
                                            Session.ProgramPoint.Speaker(
                                                created_at = speaker.created_at ?: "",
                                                id = speaker.id,
                                                person = Session.ProgramPoint.Speaker.Person(
                                                    city = person.city ?: "",
                                                    company = person.company ?: "",
                                                    country = person.country ?: "",
                                                    created_at = person.created_at ?: "",
                                                    department = person.department ?: "",
                                                    displayValue = person.displayValue ?: "",
                                                    firstName = person.firstName ?: "",
                                                    gender = person.gender ?: "",
                                                    id = person.id ?: "",
                                                    lastName = person.lastName ?: "",
                                                    state = person.state ?: "",
                                                    street = person.street ?: "",
                                                    title = person.title ?: "",
                                                    updated_at = person.updated_at ?: "",
                                                    zip = person.zip ?: ""
                                                ),
                                                person_id = speaker.person_id ?: "",
                                                position = speaker.position ?: "",
                                                program_point_id = speaker.program_point_id ?: "",
                                                updated_at = speaker.updated_at ?: ""
                                            )
                                        }
                                    },
                                    talkTime = programPoint.talkTime,
                                    updated_at = programPoint.updated_at ?: ""
                                )
                            },
                            room = Session.Room(
                                created_at = session.room.created_at ?: "",
                                event = session.room.event ?: "",
                                id = session.room.id ?: "",
                                name = session.room.name ?: "",
                                roomAddition = session.room.roomAddition ?: "",
                                updated_at = session.room.updated_at ?: ""
                            ),
                            roomOrderPosition = session.roomOrderPosition,
                            room_id = session.room_id ?: "",
                            subtitle = session.subtitle ?: "",
                            timeslotTitle = session.timeslotTitle ?: "",
                            title = session.title ?: "",
                            type = Session.Type(
                                color = session.type.color ?: "",
                                colorBackground = session.type.colorBackground ?: "",
                                colorBorder = session.type.colorBorder ?: "",
                                colorFont = session.type.colorFont ?: "",
                                colorHighlight = session.type.colorHighlight ?: "",
                                created_at = session.type.created_at ?: "",
                                description = session.type.description ?: "",
                                id = session.type.id ?: "",
                                name = session.type.name ?: "",
                                updated_at = session.type.updated_at ?: ""
                            ),
                            type_id = session.type_id ?: "",
                            updated_at = session.updated_at ?: ""
                        )
                        sessionList.add(session1)
                    }


                    viewModel1.insertSession(sessionList)

                    // Now you have the sessionList containing Session objects, you can use it as needed.
                }
                is ResourceApp.Error -> {
                    // Handle error case if necessary
                }
                is ResourceApp.Loading -> {
                    // Handle loading state if necessary
                }
            }




        })
    }

    private fun getSession_22() {
        val date="22-05-2024"
        viewModel.getSession22(progressDialog,date)
        getSession22Response()
    }

    private fun getSession22Response() {
        viewModel.getSessionLiveData.observe(this, Observer {
            response->

            when (response) {
                is ResourceApp.Success -> {
                    val responseData: ResourceApp<Sesssion_Model>? = response
                    val sessions = responseData!!.data!!.sessions
                    Log.d(ConstantsApp.TAG, "getSession22Response=>" + responseData!!.data!!.sessions)

                    val sessionList = mutableListOf<Session>()

                    for (session in sessions) {
                        val session1 = Session(
                            _id = 0,
                            begin = session.begin ?: "",
                            chair = session.chair ?: "",
                            chairs = session.chairs.mapNotNull { chair ->
                                chair.person?.let { person ->
                                    Session.Chair(
                                        created_at = chair.created_at ?: "",
                                        id = chair.id,
                                        person = Session.Chair.Person(
                                            city = person.city ?: "",
                                            company = person.company ?: "",
                                            country = person.country ?: "",
                                            created_at = person.created_at ?: "",
                                            department = person.department ?: "",
                                            displayValue = person.displayValue ?: "",
                                            firstName = person.firstName ?: "",
                                            gender = person.gender ?: "",
                                            id = person.id ?: "",
                                            lastName = person.lastName ?: "",
                                            state = person.state ?: "",
                                            street = person.street ?: "",
                                            title = person.title ?: "",
                                            updated_at = person.updated_at ?: "",
                                            zip = person.zip ?: ""
                                        ),
                                        person_id = chair.person_id ?: "",
                                        position = chair.position ?: 0,
                                        session_id = chair.session_id ?: "",
                                        updated_at = chair.updated_at ?: ""
                                    )
                                }
                            },
                            created_at = session.created_at ?: "",
                            date = session.date ?: "",
                            description = session.description ?: "",
                            end = session.end ?: "",
                            id = session.id ?: "",
                            isEposterSession = session.isEposterSession,
                            program_points = session.program_points.map { programPoint ->
                                Session.ProgramPoint(
                                    begin = programPoint.begin ?: "",
                                    created_at = programPoint.begin ?: "",
                                    discussionTime = programPoint.discussionTime,
                                    end = programPoint.end ?: "",
                                    id = programPoint.id ?: "",
                                    isEposter = programPoint.isEposter,
                                    position = programPoint.position,
                                    primaryTitle = programPoint.primaryTitle ?: "",
                                    secondaryTitle = programPoint.secondaryTitle ?: "",
                                    session_id = programPoint.session_id ?: "",
                                    speaker = programPoint.speaker ?: "",
                                    speakers = programPoint.speakers.mapNotNull { speaker ->
                                        speaker.person?.let { person ->
                                            Session.ProgramPoint.Speaker(
                                                created_at = speaker.created_at ?: "",
                                                id = speaker.id,
                                                person = Session.ProgramPoint.Speaker.Person(
                                                    city = person.city ?: "",
                                                    company = person.company ?: "",
                                                    country = person.country ?: "",
                                                    created_at = person.created_at ?: "",
                                                    department = person.department ?: "",
                                                    displayValue = person.displayValue ?: "",
                                                    firstName = person.firstName ?: "",
                                                    gender = person.gender ?: "",
                                                    id = person.id ?: "",
                                                    lastName = person.lastName ?: "",
                                                    state = person.state ?: "",
                                                    street = person.street ?: "",
                                                    title = person.title ?: "",
                                                    updated_at = person.updated_at ?: "",
                                                    zip = person.zip ?: ""
                                                ),
                                                person_id = speaker.person_id ?: "",
                                                position = speaker.position ?: "",
                                                program_point_id = speaker.program_point_id ?: "",
                                                updated_at = speaker.updated_at ?: ""
                                            )
                                        }
                                    },
                                    talkTime = programPoint.talkTime,
                                    updated_at = programPoint.updated_at ?: ""
                                )
                            },
                            room = Session.Room(
                                created_at = session.room.created_at ?: "",
                                event = session.room.event ?: "",
                                id = session.room.id ?: "",
                                name = session.room.name ?: "",
                                roomAddition = session.room.roomAddition ?: "",
                                updated_at = session.room.updated_at ?: ""
                            ),
                            roomOrderPosition = session.roomOrderPosition,
                            room_id = session.room_id ?: "",
                            subtitle = session.subtitle ?: "",
                            timeslotTitle = session.timeslotTitle ?: "",
                            title = session.title ?: "",
                            type = Session.Type(
                                color = session.type.color ?: "",
                                colorBackground = session.type.colorBackground ?: "",
                                colorBorder = session.type.colorBorder ?: "",
                                colorFont = session.type.colorFont ?: "",
                                colorHighlight = session.type.colorHighlight ?: "",
                                created_at = session.type.created_at ?: "",
                                description = session.type.description ?: "",
                                id = session.type.id ?: "",
                                name = session.type.name ?: "",
                                updated_at = session.type.updated_at ?: ""
                            ),
                            type_id = session.type_id ?: "",
                            updated_at = session.updated_at ?: ""
                        )
                        sessionList.add(session1)
                    }


                    viewModel1.insertSession(sessionList)

                    // Now you have the sessionList containing Session objects, you can use it as needed.
                }
                is ResourceApp.Error -> {
                    // Handle error case if necessary
                }
                is ResourceApp.Loading -> {
                    // Handle loading state if necessary
                }
            }




        })
    }

    private fun fetchDataSequentially() {
        showLoader()







            getDays {
                getFilterData{
                    Get_Member_Data_From_Server {
                        getSession1 {
                            hideLoader()
                            sessionManager.isDataFetched = true
                        }
                    }
                }
            }








    }

    private fun showLoader() {
        progressDialog1= ProgressDialog(this)
        progressDialog1.setMessage("Loading...")
        progressDialog1.setCancelable(false)
        progressDialog1.show()
    }

    private fun hideLoader() {
        if (::progressDialog1.isInitialized && progressDialog1.isShowing) {
            progressDialog1.dismiss()
        }
    }

    private fun getDays(callback: () -> Unit) {
        // API call for getDays
        // on success:


        val getDaysRequest= GET_DAYS_REQUEST("863f8ff3-fdd2-4d2c-b077-52f451eacfc5")
        viewModel.getDays(progressDialog,getDaysRequest)

        viewModel.getDAYSLiveData.observe(this, Observer { response ->


            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    // Check if response is not null and contains data
                    if (response != null && response.data != null) {

                         val responseData: GET_DAYS_RESPONSE? = response.data

                        Log.d(ConstantsApp.TAG, "Response Data: $responseData")

                        // Use responseData directly as it's already of type GET_DAYS_RESPONSE?
                        responseData?.let { getDaysResponse ->
                            Log.d(ConstantsApp.TAG, "getDaysResponse: ${getDaysResponse.member}")



                            val dayRoomList = mutableListOf<Get_Day_Room>()
                            for (data in getDaysResponse.member)
                            {
                                val date=data.date
                                val room=data.roomOrders
                                Log.d(ConstantsApp.TAG,""+date)
                                Log.d(ConstantsApp.TAG,""+data.roomOrders)

                                val dayRoom = Get_Day_Room(0,date, room)
                                dayRoomList.add(dayRoom)
                            }

                            /*
                                                        progressDialog1 = ProgressDialog(this).apply {
                                                            setCancelable(false)
                                                            setMessage("Data Inserting")
                                                        }*/




                            viewModel1.insert_Day_room_list(dayRoomList)
                            callback()
                        }
                    } else {
                        Log.d(ConstantsApp.TAG, "Response is null or empty")
                    }
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                    callback()
                }
                is ResourceApp.Loading->
                {
                    // showDialog()
                    //progressDialog.show()
                }
            }






        })
    }

    private fun getFilterData(callback: () -> Unit) {
        // API call for getFilterData
        // on success:

        val event="863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val hasChairOrSpeakerEngagements=true
        val pagination=false

        val getFilterRequest=GET_FILTER_REQUEST(hasChairOrSpeakerEngagements, pagination, event)
        viewModel.getFilterData(getFilterRequest,progressDialog)

        viewModel.getFilterLiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()

                    val responseData: GET_FILTER_RESPONSE? = response.data

                    Log.d(ConstantsApp.Filter, "Response Data of filter: ${responseData}")

                    responseData?.let { GET_FILTER_RESPONSE ->
                        Log.d(ConstantsApp.Filter, "Filter data: ${GET_FILTER_RESPONSE.member}")



                        val filterDataList = mutableListOf<Get_Filter_Data_Local>()
                        for (data in GET_FILTER_RESPONSE.member)
                        {
                            val id=data.id1
                            val title=data.title
                            val firstName=data.firstName
                            val lastName=data.lastName


                            val data = Get_Filter_Data_Local(0,id, title,firstName,lastName)
                            filterDataList.add(data)
                        }


                        /*  progressDialog3 = ProgressDialog(this).apply {
                              setCancelable(false)
                              setMessage("Data Inserting")
                          }*/

                        // viewModel1.insert_filter_Data(progressDialog3,filterDataList)
                        viewModel1.insert_filter_Data(filterDataList)
                        callback()
                        Filter_Data_Local_Response()

                    }
                }

                is ResourceApp.Error->
                {
                    callback()
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                    //showDialog()
                    //progressDialog.show()
                }
            }





        })

    }



    private fun Get_Member_Data_From_Server(callback: () -> Unit) {
        // API call for getSession1
        // on success:

        viewModel.getMember(progressDialog)

        viewModel.getMemberLiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()

                    try {
                        val responseData: Get_Member_Response? = response.data

                        Log.d(ConstantsApp.TAG,"GetMemberResponse=>"+responseData!!.data)

                        var member_data=responseData.data

                        //val email=mobEmailEditText.text.toString()

                        for (member in member_data)
                        {
                            val WFNR_reg_no=member.WFNR_reg_no
                            val city=member.city
                            val country=member.country
                            val created_at=member.created_at
                            val deleted_at=member.deleted_at
                            val email=member.email
                            val fname=member.fname
                            val id=member.id
                            val is_deleted=member.is_deleted
                            val isactive=member.isactive
                            val lname=member.lname
                            val login_count=member.login_count
                            val mobile=member.mobile
                            val reg_type=member.reg_type
                            val title=member.title
                            val updated_at=member.updated_at
                            val registrationDataLocalRequest=
                                LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                                    id.toString()
                                )
                            viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                            callback()
                            saveRegistrationDataLocalResponse(member.email)

                        }


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
                   // showDialog()
                    // progressDialog.show()
                }
            }




            /*val data=response.data!!.data

            val email=mobEmailEditText.text.toString()

            for (member in data)
            {
                if(email.equals(member.email))
                {
                      val fname=editTextMemberName.text.toString()
                          val email=mobEmailEditText.text.toString()
                          val WFNR_reg_no=membershipNoEditText.text.toString()
                          val city=editTextCity.text.toString()
                          val country=editTextCountry.text.toString()
                          val registrationDataLocalRequest=LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                              member.id.toString()
                          )
                          viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                          saveRegistrationDataLocalResponse()
                }

            }*/
        })

    }

    private fun getSession1(callback: () -> Unit) {
        // API call for getSession1
        // on success:

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-22"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)

        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)

        viewModel.getSessionRequest1LiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {

                    try {

                        Log.d(ConstantsApp.TAG,""+response.data)

                        progressDialog.dismiss()
                        //getSession4()

                        val responseData: GET_SESSION_RESPONSE? = response.data
                        Log.d(ConstantsApp.TAG, "GetSessionRequest1Response: ${responseData!!.member}")

                        Log.d(ConstantsApp.TAG,""+responseData.member.size)

                        val sessionList = mutableListOf<Get_Session>()



                        for (member in responseData.member)
                        {

                            val session = Get_Session(
                                _id = 0,
                                id = member.id ?: "",
                                type = member.type ?: "",
                                begin = member.begin ?: "",
                                date = member.date ?: "",
                                description = member.description ?: "",
                                id1=member.id1?:"",
                                chairs = member.chairs,
                                end = member.end ?: "",
                                programPoints = member.programPoints,
                                room = member.room,
                                timeslotTitle = member.timeslotTitle ?: "",
                                title = member.title ?: "",
                                topics = member.topics ?: emptyList(),

                                )
                            sessionList.add(session)
                        }

                        progressDialog.dismiss()

                        /*progressDialog2 = ProgressDialog(this).apply {
                            setCancelable(false)
                            setMessage("Data Inserting")
                        }*/



                        insertSessionsInBatches(sessionList) {
                            // Callback to execute after all sessions are inserted
                            callback()
                        }
                       // viewModel1.insert_Session_list(sessionList)
                       // callback()

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
                    //showDialog()
                    //progressDialog.show()
                }
            }
        })

    }

    private fun insertSessionsInBatches(sessionList: List<Get_Session>, callback: () -> Unit) {
        val batchSize = 20 // Define your batch size
        val totalSessions = sessionList.size
        var startIndex = 0

        while (startIndex < totalSessions) {
            val endIndex = kotlin.math.min(startIndex + batchSize, totalSessions)
            val batchSessions = sessionList.subList(startIndex, endIndex)as MutableList<Get_Session>
            viewModel1.insert_Session_list(batchSessions)
            startIndex += batchSize
        }

        callback()
    }

   /* private fun getSession4() {
        // API call for getSession4
        // on success:

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-25"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)
        // val getSessionRequest1=GET_SESSION_REQUEST(eventId,date)
        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)

        viewModel.getSessionRequest1LiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    try {
                        val responseData: GET_SESSION_RESPONSE? = response.data
                        Log.d(ConstantsApp.TAG, "GetSessionRequest1Response: ${responseData!!.member}")

                        val sessionList = mutableListOf<Get_Session>()



                        for (member in responseData.member)
                        {

                            val session = Get_Session(
                                _id = 0,
                                id = member.id ?: "",
                                type = member.type ?: "",
                                begin = member.begin ?: "",
                                date = member.date ?: "",
                                description = member.description ?: "",
                                id1=member.id1?:"",
                                chairs = member.chairs,
                                end = member.end ?: "",
                                programPoints = member.programPoints,
                                room = member.room,
                                timeslotTitle = member.timeslotTitle ?: "",
                                title = member.title ?: "",
                                topics = member.topics ?: emptyList(),

                                )
                            sessionList.add(session)
                        }






                          progressDialog2 = ProgressDialog(this).apply {
                              setCancelable(false)
                              setMessage("Data Inserting")
                          }
                        viewModel1.insert_Session_list(sessionList)

                        //getSession3()

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
                   // showDialog()
                    progressDialog.show()
                }
            }
        })

    }*/

    private fun get_All_ITINERARY(callback: () -> Unit) {
        // API call for get_All_ITINERARY
        // on success:
        callback()
    }

    private fun get_All_Topic(callback: () -> Unit) {
        // API call for get_All_Topic
        // on success:
        callback()
    }

    private fun scheduleJob() {
        val componentName = ComponentName(this, MyJobService2::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setRequiresCharging(false) // Set your requirements for the job
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)
            .build()
        // .setPeriodic(24 * 60 * 60 * 1000) // Repeat every 24 hours

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(jobInfo)


        /*    val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(context, MyJobIntentService::class.java)

            val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                .setPersisted(true)
                .setPeriodic(24 * 60 * 60 * 1000) // Repeat every 24 hours
                .build()

            jobScheduler.schedule(jobInfo)*/
    }

    private fun Get_Member_Data_From_Server() {
        viewModel.getMember(progressDialog)
        GetMemberResponse()
    }

    private fun GetMemberResponse() {
        viewModel.getMemberLiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()

                    try {
                        val responseData: Get_Member_Response? = response.data

                        Log.d(ConstantsApp.TAG,"GetMemberResponse=>"+responseData!!.data)

                        var member_data=responseData.data

                        //val email=mobEmailEditText.text.toString()

                        for (member in member_data)
                        {
                            val WFNR_reg_no=member.WFNR_reg_no
                            val city=member.city
                            val country=member.country
                            val created_at=member.created_at
                            val deleted_at=member.deleted_at
                            val email=member.email
                            val fname=member.fname
                            val id=member.id
                            val is_deleted=member.is_deleted
                            val isactive=member.isactive
                            val lname=member.lname
                            val login_count=member.login_count
                            val mobile=member.mobile
                            val reg_type=member.reg_type
                            val title=member.title
                            val updated_at=member.updated_at
                                val registrationDataLocalRequest=
                                    LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                                        id.toString()
                                )
                                viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                                saveRegistrationDataLocalResponse(member.email)

                        }


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
                    //showDialog()
                    progressDialog.show()
                }
            }




            /*val data=response.data!!.data

            val email=mobEmailEditText.text.toString()

            for (member in data)
            {
                if(email.equals(member.email))
                {
                      val fname=editTextMemberName.text.toString()
                          val email=mobEmailEditText.text.toString()
                          val WFNR_reg_no=membershipNoEditText.text.toString()
                          val city=editTextCity.text.toString()
                          val country=editTextCountry.text.toString()
                          val registrationDataLocalRequest=LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                              member.id.toString()
                          )
                          viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                          saveRegistrationDataLocalResponse()
                }

            }*/
        })
    }

    private fun saveRegistrationDataLocalResponse(email: String) {
        viewModel1.saveSuccess.observe(this, Observer {
                response->

            Log.d(ConstantsApp.TAG,response.toString())
            when(response)
            {
                "Success"->
                {

                    // sessionManager.setLogin("1")
                    /*sessionManager.setUserEmaiID(email)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()*/
                   // Log.d(ConstantsApp.TAG,"Success")
                }
            }
        })
    }

    private fun get_All_Topic() {
        viewModel1.get_All_Topic.observe(this, Observer {
                response->
            Log.d(ConstantsApp.TAG,"get_All_ITINERARY=>"+response)

            if (response.size>0)
            {
                // Assuming response is a list of Send_Favourite_Request objects
                val itineraryList: List<Topic_Notes>? = response

                // Check if the list is not null before attempting to clear it
                itineraryList?.let {
                    // Create a new list to store modified items
                    val modifiedList = mutableListOf<Send_Favourite_Request>()

                    // Iterate through each item in the list
                    it.forEach { itineraryItem ->
                        // Extract required fields and create a new Send_Favourite_Request object
                        val modifiedItem = Topic_Note_Request(
                            member_id = itineraryItem.wcnr_id,
                            topic_id = itineraryItem.wcnr_name,
                            notes = itineraryItem.topic_note
                        )

                        // Add the modified item to the new list
                        send_To_notes_Server(modifiedItem)
                    }

                    // Now you can send the modified list to the server using an API call

                }
            }










        })
    }

    private fun send_To_notes_Server(data: Topic_Note_Request) {

        viewModel.store_Notes(progressDialog,data)
        Store_Notes_Response()

    }

    private fun Store_Notes_Response() {
        viewModel.storeNotesLiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    when(response.data!!.status_code)
                    {
                        200->
                        {
                            Log.d(ConstantsApp.TAG,"Store_Favourite_Response=>"+response.data.message)
                        }
                    }
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                    //showDialog()
                    //progressDialog.show()
                }
            }
        })
    }

    private fun get_All_ITINERARY() {
       viewModel1.get_All_ITINERARY.observe(this, Observer {
           response->

           if (response.size>0)
           {
               Log.d(ConstantsApp.TAG,"get_All_ITINERARY=>"+response)







               // Assuming response is a list of Send_Favourite_Request objects
               val itineraryList: List<Topic_Itinerary>? = response

               // Check if the list is not null before attempting to clear it
               itineraryList?.let {
                   // Create a new list to store modified items
                   val modifiedList = mutableListOf<Send_Favourite_Request>()

                   // Iterate through each item in the list
                   it.forEach { itineraryItem ->
                       // Extract required fields and create a new Send_Favourite_Request object
                       val modifiedItem = Send_Favourite_Request(
                           member_id = itineraryItem.wcnr_id,
                           topic_id = itineraryItem.topic_id,
                           is_fav = itineraryItem.is_fav
                       )

                       // Add the modified item to the new list
                       send_To_Favourite_Server(modifiedItem)
                   }

                   // Now you can send the modified list to the server using an API call

               }
           }



       })
    }

    private fun send_To_Favourite_Server(data: Send_Favourite_Request) {

        viewModel.store_favourites(progressDialog,data)
        Store_Favourite_Response()

    }

    private fun Store_Favourite_Response() {
        viewModel.storeFavouriteLiveData.observe(this, Observer {
            response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    when(response.data!!.status_code)
                    {
                        200->
                        {
                           Log.d(ConstantsApp.TAG,"Store_Favourite_Response=>"+response.data.message)
                        }
                    }
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                   // showDialog()
                   //progressDialog.show()
                }
            }
        })
    }

    private fun showDialog() {

        val progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set layout parameters to center the dialog
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(progressDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER

        progressDialog.window?.attributes = layoutParams

        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.show()

        // Example: Dismiss the dialog after a delay (simulating a task completion)
        val imageViewLoading: ImageView = progressDialog.findViewById(R.id.imageViewLoading)

        Glide.with(this)
            .asGif()
            .load(R.raw.loader)
            .into(imageViewLoading)

        imageViewLoading.postDelayed({
            progressDialog.dismiss()
        }, 7000)
    }

    private fun getFilterData() {
        val event="863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val hasChairOrSpeakerEngagements=true
        val pagination=false

        val getFilterRequest=GET_FILTER_REQUEST(hasChairOrSpeakerEngagements, pagination, event)
        viewModel.getFilterData(getFilterRequest,progressDialog)
        GetFilterResponse()

    }

    private fun GetFilterResponse() {
       viewModel.getFilterLiveData.observe(this, Observer {
           response->

           when(response)
           {
               is ResourceApp.Success->
               {
                   progressDialog.dismiss()

                   val responseData: GET_FILTER_RESPONSE? = response.data

                   Log.d(ConstantsApp.Filter, "Response Data of filter: ${responseData}")

                   responseData?.let { GET_FILTER_RESPONSE ->
                       Log.d(ConstantsApp.Filter, "Filter data: ${GET_FILTER_RESPONSE.member}")



                       val filterDataList = mutableListOf<Get_Filter_Data_Local>()
                       for (data in GET_FILTER_RESPONSE.member)
                       {
                           val id=data.id1
                           val title=data.title
                           val firstName=data.firstName
                           val lastName=data.lastName


                           val data = Get_Filter_Data_Local(0,id, title,firstName,lastName)
                           filterDataList.add(data)
                       }


                     /*  progressDialog3 = ProgressDialog(this).apply {
                           setCancelable(false)
                           setMessage("Data Inserting")
                       }*/

                      // viewModel1.insert_filter_Data(progressDialog3,filterDataList)
                       viewModel1.insert_filter_Data(filterDataList)
                       Filter_Data_Local_Response()
                   }
               }

               is ResourceApp.Error->
               {
                   progressDialog.dismiss()
               }
               is ResourceApp.Loading->
               {
                   //showDialog()
                   progressDialog.show()
               }
           }





       })
    }

    private fun Filter_Data_Local_Response() {
        viewModel1.saveSuccess.observe(this, Observer {
            response->
            Log.d(ConstantsApp.Filter,"Filter_Data_Local_Response=>"+response)
            when(response)
            {
                "Success"->
                {

                }
            }
        })
    }

    private fun getSession1() {

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-22"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)

        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)
        GetSessionRequest1Response()
    }




 /*   private fun getSession4() {

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-25"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)
       // val getSessionRequest1=GET_SESSION_REQUEST(eventId,date)
        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)
        GetSessionRequest1Response4()
    }*/

    private fun GetSessionRequest1Response4() {
        viewModel.getSessionRequest1LiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    try {
                        val responseData: GET_SESSION_RESPONSE? = response.data
                        Log.d(ConstantsApp.TAG, "GetSessionRequest1Response: ${responseData!!.member}")

                        val sessionList = mutableListOf<Get_Session>()



                        for (member in responseData.member)
                        {

                            val session = Get_Session(
                                _id = 0,
                                id = member.id ?: "",
                                type = member.type ?: "",
                                begin = member.begin ?: "",
                                date = member.date ?: "",
                                description = member.description ?: "",
                                id1=member.id1?:"",
                                chairs = member.chairs,
                                end = member.end ?: "",
                                programPoints = member.programPoints,
                                room = member.room,
                                timeslotTitle = member.timeslotTitle ?: "",
                                title = member.title ?: "",
                                topics = member.topics ?: emptyList(),

                                )
                            sessionList.add(session)
                        }






                      /*  progressDialog2 = ProgressDialog(this).apply {
                            setCancelable(false)
                            setMessage("Data Inserting")
                        }*/
                        viewModel1.insert_Session_list(sessionList)
                        //getSession3()

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
                   showDialog()
                   // progressDialog.show()
                }
            }
        })
    }

    private fun GetSessionRequest1Response() {
        viewModel.getSessionRequest1LiveData.observe(this, Observer {
            response->

            when(response)
            {
                is ResourceApp.Success->
                {

                    try {

                        progressDialog.dismiss()

                        //getSession4()


                        val responseData: GET_SESSION_RESPONSE? = response.data
                        Log.d(ConstantsApp.TAG, "GetSessionRequest1Response: ${responseData!!.member}")

                        val sessionList = mutableListOf<Get_Session>()



                        for (member in responseData.member)
                        {

                            val session = Get_Session(
                                _id = 0,
                                id = member.id ?: "",
                                type = member.type ?: "",
                                begin = member.begin ?: "",
                                date = member.date ?: "",
                                description = member.description ?: "",
                                id1=member.id1?:"",
                                chairs = member.chairs,
                                end = member.end ?: "",
                                programPoints = member.programPoints,
                                room = member.room,
                                timeslotTitle = member.timeslotTitle ?: "",
                                title = member.title ?: "",
                                topics = member.topics ?: emptyList(),

                                )
                            sessionList.add(session)
                        }



                        progressDialog2 = ProgressDialog(this).apply {
                            setCancelable(false)
                            setMessage("Please Wait")
                        }
                        viewModel1.insert_Session_list(progressDialog2,sessionList)



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
                   // showDialog()
                     progressDialog.show()
                }
            }
        })
    }

    private fun getSession4() {
        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-25"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)
        // val getSessionRequest1=GET_SESSION_REQUEST(eventId,date)
        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)

        getSession4Response()


    }

    private fun getSession4Response() {
        viewModel.getSessionRequest1LiveData.observe(this, Observer {
                response->

            when(response)
            {
                is ResourceApp.Success->
                {

                    try {

                        progressDialog.dismiss()

                        val responseData: GET_SESSION_RESPONSE? = response.data
                        Log.d(ConstantsApp.TAG, "GetSessionRequest1Response: ${responseData!!.member}")

                        val sessionList = mutableListOf<Get_Session>()



                        for (member in responseData.member)
                        {

                            val session = Get_Session(
                                _id = 0,
                                id = member.id ?: "",
                                type = member.type ?: "",
                                begin = member.begin ?: "",
                                date = member.date ?: "",
                                description = member.description ?: "",
                                id1=member.id1?:"",
                                chairs = member.chairs,
                                end = member.end ?: "",
                                programPoints = member.programPoints,
                                room = member.room,
                                timeslotTitle = member.timeslotTitle ?: "",
                                title = member.title ?: "",
                                topics = member.topics ?: emptyList(),

                                )
                            sessionList.add(session)
                        }



                        progressDialog2 = ProgressDialog(this).apply {
                            setCancelable(false)
                            setMessage("Please Wait")
                        }
                        viewModel1.insert_Session_list(progressDialog2,sessionList)



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
                    // showDialog()
                    progressDialog.show()
                }
            }
        })
    }


    private fun getDays() {
        val getDaysRequest= GET_DAYS_REQUEST("863f8ff3-fdd2-4d2c-b077-52f451eacfc5")
        viewModel.getDays(progressDialog,getDaysRequest)
        getDaysResponse()
    }

    private fun getDaysResponse() {
        viewModel.getDAYSLiveData.observe(this, Observer { response ->


            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    // Check if response is not null and contains data
                    if (response != null && response.data != null) {
                        val responseData: GET_DAYS_RESPONSE? = response.data

                        Log.d(ConstantsApp.TAG, "Response Data: $responseData")

                        // Use responseData directly as it's already of type GET_DAYS_RESPONSE?
                        responseData?.let { getDaysResponse ->
                            Log.d(ConstantsApp.TAG, "getDaysResponse: ${getDaysResponse.member}")



                            val dayRoomList = mutableListOf<Get_Day_Room>()
                            for (data in getDaysResponse.member)
                            {
                                val date=data.date
                                val room=data.roomOrders
                                Log.d(ConstantsApp.TAG,""+date)
                                Log.d(ConstantsApp.TAG,""+data.roomOrders)

                                val dayRoom = Get_Day_Room(0,date, room)
                                dayRoomList.add(dayRoom)
                            }

/*
                            progressDialog1 = ProgressDialog(this).apply {
                                setCancelable(false)
                                setMessage("Data Inserting")
                            }*/




                            viewModel1.insert_Day_room_list(dayRoomList)
                        }
                    } else {
                        Log.d(ConstantsApp.TAG, "Response is null or empty")
                    }
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                   // showDialog()
                    progressDialog.show()
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
            dismiss()

        }

        sessionManager = SessionManager(this)



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




    private fun checkForAppUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }

        appUpdateInfoTask.addOnFailureListener { exception ->
            Log.d("mytag", "appUpdateInfoTask.addOnFailureListener =>" + exception.message)
        }
    }
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
        // handle callback
        if (result.resultCode == RESULT_OK) {
            Log.d("mytag", "Update flow succeeded!")
            // Navigate to the splash screen
            /*startActivity(Intent(this, SplashActivity::class.java))
            finish()*/
        // Optionally, finish this activity if you don't want to keep it in the back stack

            openApp(this)
        } else {
            Log.d("mytag", "Update flow failed! Result code: " + result.resultCode)

          /*  startActivity(Intent(this, SplashActivity::class.java))
            finish()*/
            openApp(this)
            // If the update is canceled or fails, you can request to start the update again.
            // Optionally, you can also navigate to the splash screen or handle the failure appropriately.
        }
    }




    private fun hasUserAcceptedPrivacyPolicy(): Boolean {
        // Retrieve the user's acceptance status from SharedPreferences
        return sharedPreferences.getBoolean(KEY_ACCEPTED_PRIVACY_POLICY, false)
    }

    private fun setUserAcceptedPrivacyPolicy() {
        // Save the user's acceptance status in SharedPreferences
        sharedPreferences.edit().putBoolean(KEY_ACCEPTED_PRIVACY_POLICY, true).apply()
    }

    companion object {
        private const val KEY_ACCEPTED_PRIVACY_POLICY = "accepted_privacy_policy"
    }

    @SuppressLint("MissingInflatedId")
    private var isPrivacyPolicyAccepted = false

    private fun showPrivacyPolicyDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_privacy_policy, null)

        // Initialize your dialog views
        val textPrivacyPolicy: TextView = view.findViewById(R.id.textprivacypolicy)
        val btnAcceptContinue: TextView = view.findViewById(R.id.acceptButton)

        // Set privacy policy text

        // Set click listener for the Accept & Continue button
        btnAcceptContinue.setOnClickListener {
            // Set the flag to true to indicate that the user has accepted
            setUserAcceptedPrivacyPolicy()
            Log.d("mytag","isPrivacyPolicyAccepted=>true"+isPrivacyPolicyAccepted)
            // Close the dialog
            privacyPolicyDialog?.dismiss()
            // Add code to start your app's main functionality
        }

        builder.setView(view)
        privacyPolicyDialog = builder.create()

        // Disable outside touch dismissal
        privacyPolicyDialog?.setCancelable(false)


        // Set top and bottom margins for the dialog window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(privacyPolicyDialog?.window?.attributes)

        // Set top margin in pixels
        val topMarginInPixels = resources.getDimensionPixelSize(R.dimen.margin_80dp)
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        layoutParams.y = topMarginInPixels

        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        privacyPolicyDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        privacyPolicyDialog?.window?.attributes = layoutParams

        // Set a listener for the back button to handle the back press
        privacyPolicyDialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.action == android.view.KeyEvent.ACTION_UP) {
                // Handle back button press here
                // For example, show a toast indicating that the user needs to accept the policy
                return@setOnKeyListener true
            } else {
                false
            }
        }
        privacyPolicyDialog?.show()
    }
    override fun onResume() {
        super.onResume()
        // Ensure to check for app updates when the activity resumes
        checkForAppUpdates()
    }


    private  fun loadFragment(fragment: Fragment){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.relativelayout_container,fragment)
    transaction.commit()
}



    override fun onBackPressed() {
        // Check if the privacy policy dialog is showing
        if (privacyPolicyDialog?.isShowing == true) {
            // Optionally, show a toast to inform the user to accept the policy
            // Toast.makeText(this, "Please accept the privacy policy", Toast.LENGTH_SHORT).show()
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            // If the back stack is empty, show exit confirmation dialog
            showExitConfirmationDialog()
        } else {
            // Otherwise, proceed with the default back press behavior
            super.onBackPressed()
        }
    }

    private fun showExitConfirmationDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.exit_app)
        // Find views in the custom dialog layout
        val confirmationText: TextView = dialog.findViewById(R.id.textViewLogoutConfirmation)
        val buttonYes: Button = dialog.findViewById(R.id.buttonYes)
        val buttonNo: Button = dialog.findViewById(R.id.buttonNo)
        // Set confirmation text
        confirmationText.text = "Do you want to exit the app?"
        buttonYes.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
        buttonNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
    }


}
fun openApp(context: Context) {
    val packageName = context.packageName
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    launchIntent?.let {
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(launchIntent)
    }
}

    private fun sendToken(deviceToken: String) {



        val deviceType = "Android"
        val deviceModel = "Samsung Galaxy S21"
        val deviceOS = "Android 12"
        val location = "Mumbai"
        val imeiNo = "123456789012345"

        // Make API call to generate token
        val call = RetrofitClient1.apiService.generateToken(
            deviceType,
            deviceModel,
            deviceOS,
            location,
            imeiNo,
            deviceToken,
            0
        )

        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    if (tokenResponse != null) {
                        val token = tokenResponse.message
                        Log.d("mytag", "Token: $token")
                    } else {
                        Log.e("mytag", "Token response body is null")
                    }
                } else {
                    Log.e("mytag", "Failed to retrieve token: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            }

        })
    }










