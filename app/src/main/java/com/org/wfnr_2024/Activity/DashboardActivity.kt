// DashboardActivity.kt
package com.org.wfnr_2024.Activity

import Sharepreference_login
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Adapter.MyAdapter
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_REQUEST
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.Utils.SessionManager_Login
import com.org.wfnr_2024.ViewModel.ResourceApp
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var images: MutableList<Int>
    private lateinit var texts: MutableList<String>
    private lateinit var sharedPreferencesManager: Sharepreference_login

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var progressDialog: ProgressDialog
    lateinit var progressDialog1: ProgressDialog
    lateinit var progressDialog2: ProgressDialog
    lateinit var progressDialog3: ProgressDialog

    lateinit var sessionManager: SessionManager
    lateinit var sessionManager_login: SessionManager_Login

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        getViewModel()
        createRoomDatabase()








       /* if (sessionManager.isDataFetched==false)
        {
            if (ConstantsApp.checkInternetConenction(applicationContext))
            {

                lifecycleScope.launch {
                    val date="2024-05-24"
                    viewModel1.deleteData3(date)

                    val date2="2024-05-23"
                    viewModel1.deleteData3(date2)

                    getSession2()

                    getSession3()


                    sessionManager.isDataFetched=true
                }

            }
            else
            {
                Toast.makeText(applicationContext, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        }*/








        val Dashboard_back = findViewById<ImageView>(R.id.Dashboard_back)
        sharedPreferencesManager = Sharepreference_login(this)

        val linearLayout = findViewById<LinearLayout>(R.id.grid)
        val img1 = findViewById<ImageView>(R.id.notification)
        val img2 = findViewById<ImageView>(R.id.program)

        val layout = findViewById<LinearLayout>(R.id.layout_dashboard)


        layout.setOnClickListener {



            val isLogin=sessionManager.getLogin()

            val keep_login=sessionManager_login.getKeepLogin()

            when(keep_login)
            {
                true->
                {

                    val isDirectLogin=sessionManager_login.getDirectLogin()


                    when(isDirectLogin)
                    {
                        true->
                        {
                            when(isLogin)
                            {
                                "1"->
                                {
                                    val intent = Intent(this, MyWCNRSection::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else->
                                {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                        false->
                        {

                          /*  val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()*/

                            when(isLogin)
                            {
                                "1"->
                                {
                                    val intent = Intent(this, MyWCNRSection::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else->
                                {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }

                        else -> {}
                    }

                }
                false->
                {
                    val intent = Intent(this, RegistrationActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> {}
            }

        }







        // Set OnClickListener to the LinearLayout
        img1.setOnClickListener {
            // Start your activity here
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        img2.setOnClickListener {
            // Start your activity here
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }



        Dashboard_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Sample images and texts
        images = mutableListOf(
            R.drawable.commette,
//            R.drawable.scientificprogram,
            R.drawable.contactus,
//            R.drawable.dash_notification,
//            R.drawable.wcnrsction,
            R.drawable.aboutus
        )
        texts = mutableListOf(
            "Committee",
//            "  Scientific \nProgramme",

//            "Notification",
//            "My WCNR \n  Section",
            "Contact Us",
            "Info",
        )

        // Find the LinearLayout by its ID



        adapter = MyAdapter(this, images, texts)
        recyclerView.adapter = adapter
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
        sessionManager_login= SessionManager_Login(this)



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

    private fun getSession2() {

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-23"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1=GET_SESSION_REQUEST(eventId,date,groups, pagination)
        // val getSessionRequest1=GET_SESSION_REQUEST(eventId,date)
        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)
        GetSessionRequest1Response2()
    }

    private fun GetSessionRequest1Response2() {
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

    private fun getSession3() {

        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = "2024-05-24"
        val groups = listOf("session:item:read")
        val pagination = false
        val getSessionRequest1= GET_SESSION_REQUEST(eventId,date,groups, pagination)
        //val getSessionRequest1=GET_SESSION_REQUEST(eventId,date)
        viewModel.getSessionRequest1(progressDialog,getSessionRequest1)
        GetSessionRequest1Response3()
    }

    private fun GetSessionRequest1Response3() {
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

                            Log.d(ConstantsApp.TAG,"member programPoints=>"+member.programPoints)


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
                    catch (e :Exception)
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
                   // showDialog()
                }
            }
        })
    }

    private fun checkLoginStatus(): Boolean {
        // Use SharedPreferences to retrieve the login status or token
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Return the login status
        return isLoggedIn
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
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
}
