package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.MenuAdapter
import com.org.wfnr_2024.Interface.MenuCallBack
import com.org.wfnr_2024.Model.ITINERARY.Send_Favourite_Request
import com.org.wfnr_2024.Model.Note.Topic_Note_Request
import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_Menu
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

class MyWCNRSection : AppCompatActivity(), MenuCallBack, View.OnClickListener {

    lateinit var RecyclerView_menu:RecyclerView

    lateinit var MenuAdapter: MenuAdapter
    lateinit var Event_backbtn:ImageView


    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager:SessionManager
    var MenuArrayList:ArrayList<Section_Menu>?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wcnrsection)

        getViewModel()
        createRoomDatabase()

        sessionManager=SessionManager(this)


        MenuArrayList= ArrayList()

        RecyclerView_menu=findViewById(R.id.RecyclerView_menu)
        Event_backbtn=findViewById(R.id.Event_backbtn)

        MenuArrayList!!.add(Section_Menu("My Profile",R.drawable.my_profile,))
        MenuArrayList!!.add(Section_Menu("My Questions",R.drawable.my_question))
        MenuArrayList!!.add(Section_Menu("My Notes",R.drawable.my_notes))
        MenuArrayList!!.add(Section_Menu("My Itinerary",R.drawable.my_fav))
        MenuArrayList!!.add(Section_Menu("My WCNR 2024 Feedback",R.drawable.my_feedback))
        MenuArrayList!!.add(Section_Menu("Logout",R.drawable.baseline_logout_24))

        updateRecyclerView(RecyclerView_menu, MenuArrayList!!)

        Event_backbtn.setOnClickListener(this)


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



    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun updateRecyclerView(recyclerView: RecyclerView, MenuArrayList: ArrayList<Section_Menu>) {

        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.visibility = View.VISIBLE
        // noTopicTextView.visibility = View.GONE

        // Initialize and set the adapter

        MenuAdapter = MenuAdapter(MenuArrayList,this,this)
        recyclerView.adapter = MenuAdapter


    }

    override fun menuClicked(data: Section_Menu, position: Int, view: View) {
        when(data.label)
        {
            "My WCNR 2024 Feedback"->
            {
                gotoScreen(this,MyFeedBackActivity::class.java)
            }
            "My Itinerary"->
            {
                gotoScreen(this,MyItineraryActivity::class.java)
            }
            "My Questions"->
            {
                gotoScreen(this,MyQuestionActivity::class.java)
            }
            "My Notes"->
            {
                gotoScreen(this,MyNotesActivity::class.java)
            }
            "My Profile"->
            {
                gotoScreen(this,WCNR_MyProfile::class.java)

            }
            "Logout"->
            {

                showMessageDialog()
            }
        }
    }

    fun gotoScreen(context: Context?, cls: Class<*>?) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Event_backbtn->
            {
                gotoScreen(this,DashboardActivity::class.java)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showMessageDialog() {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_logout_layout, null)


        val TextView_Yes: TextView =dialogView.findViewById(R.id.TextView_Yes)
        val TextView_No: TextView =dialogView.findViewById(R.id.TextView_No)

        dialog.setContentView(dialogView)



        TextView_No.setOnClickListener {

            dialog.dismiss()
        }




        TextView_Yes.setOnClickListener {

            get_All_ITINERARY()
            get_All_Topic()
            GotoMain()

            dialog.dismiss()

        }
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog












        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    private fun GotoMain() {
        sessionManager.clearCache()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

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
                    progressDialog.show()
                }
            }
        })
    }


}