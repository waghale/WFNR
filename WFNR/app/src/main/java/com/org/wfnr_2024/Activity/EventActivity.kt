package com.org.wfnr_2024.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Adapter.CustomSpinnerAdapter
import com.org.wfnr_2024.Adapter.SessionAdapter
import com.org.wfnr_2024.Adapter.SessionAdapter1
import com.org.wfnr_2024.Model.Days.Day_get_api
import com.org.wfnr_2024.Model.SessionModel.HydraMember
import com.org.wfnr_2024.Model.SessionModel.SessionDataModel
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Room_Database.ROOM_DAO.Days_DAO
import com.org.wfnr_2024.Room_Database.ROOM_DAO.SessionDataClass_DAO
import com.org.wfnr_2024.Room_Database.WFNR_Repository
import com.org.wfnr_2024.Room_Database.WFNR_Room_Database
import com.org.wfnr_2024.Room_Database.WFNR_ViewModel
import com.org.wfnr_2024.Room_Database.WFNR_ViewModelFactory
import com.org.wfnr_2024.Utils.RetrofitClient
import com.org.wfnr_2024.Utils.SharedPreferencesManager1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventActivity : AppCompatActivity() {

    lateinit var adapter:SessionAdapter
    lateinit var adapter1: SessionAdapter1
    lateinit var spinner: Spinner
    lateinit var customSpinnerAdapter: CustomSpinnerAdapter

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerView_session: RecyclerView
    private var selectedRoomName: String? = null
    private lateinit var sharedPreferencesManager: SharedPreferencesManager1
    lateinit var viewModel1: WFNR_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        sharedPreferencesManager = SharedPreferencesManager1(this)
        showDialog()

        if (isInternetAvailable()) {
            makeDaysAPICall()
        } else {
            showNoInternetAlert("Please connect to the internet")
        }

        // Initialize views
        spinner = findViewById(R.id.spinner_dates)

        // Get and display the selected date from SharedPreferences
        val selectedDate = sharedPreferencesManager.getSelectedDate()
       Log.d("mytag","selectedDate in shareprefrence"+selectedDate)

        val Event_backbtn = findViewById<ImageView>(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        createRoomDatabase()

    }

    private fun createRoomDatabase() {
        val database = WFNR_Room_Database.getDatabase(this)

        val SessionDataClass_DAO : SessionDataClass_DAO =database.SessionDataClass_DAO()
        val Days_DAO : Days_DAO =database.Days_DAO()


        val repository = WFNR_Repository(SessionDataClass_DAO,Days_DAO,database)

        viewModel1 = ViewModelProvider(this, WFNR_ViewModelFactory(repository)).get(WFNR_ViewModel::class.java)
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
            }, 2000)
        }


    override fun onResume() {
        super.onResume()

        spinner.onItemSelectedListener=object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               Log.d("mytag","selected item=>"+parent!!.getItemAtPosition(position).toString())
                var selected_date=parent.getItemAtPosition(position).toString()
                // Save selected date using SharedPreferencesManager
                sharedPreferencesManager.saveSelectedDate(selected_date)
                // Check internet connectivity before making API call
                if (isInternetAvailable()) {
                    callAPI(selected_date)
                } else {
                    showNoInternetAlert("Please connect to the internet")
                }
               // callAPI(selected_date)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

       // callAPI(selected_date)
    }

    private fun showNoInternetAlert(message:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.intenet_alert)

        // Find views in the custom dialog layout
        val notificationTitle: TextView = dialog.findViewById(R.id.notificationTitle)
        val textViewLogoutConfirmation: TextView = dialog.findViewById(R.id.textViewLogoutConfirmation)
        val buttonYes: Button = dialog.findViewById(R.id.buttonYes)
        val buttonNo: Button = dialog.findViewById(R.id.buttonNo)

        // Set notification title and confirmation text
        notificationTitle.text = "Connectivity Issue"
        textViewLogoutConfirmation.text = message

        // Set click listener for the Yes button
        buttonYes.setOnClickListener {
            // Handle the Yes button click event
            dialog.dismiss()
        }

        // Set click listener for the No button
        buttonNo.setOnClickListener {
            // Handle the No button click event
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

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    fun makeDaysAPICall() {
        val DaysService = RetrofitClient.apiService

        val call = DaysService.getDays(1, 30, false, "863f8ff3-fdd2-4d2c-b077-52f451eacfc5")
        call.enqueue(object : Callback<Day_get_api> {
            override fun onResponse(call: Call<Day_get_api>, response: Response<Day_get_api>) {
                if (response.isSuccessful) {
                    val dayData = response.body()


//                    viewModel1.insertDate()


                    // Handle the received data here
                    Log.d("mytag","dayData=>"+dayData)


                    val hydraMembers: ArrayList<com.org.wfnr_2024.Model.Days.HydraMember> = dayData!!.member
                    val dates: List<String> = hydraMembers.map { it.date }
                    if (dates != null) {
                        val daysList = dayData.member//
                        // Iterate over each HydraMember
                        hydraMembers.forEach { hydraMember ->
                            val roomOrders = hydraMember.roomOrders

                            // Iterate over RoomOrders for each HydraMember
                            roomOrders.forEach { roomOrder ->
                                val roomPosition = roomOrder.position
                                val roomName = roomOrder.name
                                // Use roomPosition as needed (e.g., assign to UI elements)
                                Log.d("mytag", "Room: $roomName, Position: $roomPosition")
                            }
                        }
                        // Extracting dates from HydraMember objects
                        val customAdapter = CustomSpinnerAdapter( applicationContext,dates)
                        spinner?.adapter = customAdapter
                    }
                } else {
                    // Handle error
                    val errorMessage = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Day_get_api>, t: Throwable) {
                // Handle failure
                val errorMessage = "Failed to fetch data: ${t.message}"
            }
        })
    }


    private fun callAPI(selected_date: String) {
        val date = selected_date
        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"


        Log.d("mytag","callAPI")




        RetrofitClient.apiService.getProgramItems(date, eventId).enqueue(object : Callback<SessionDataModel> {
            override fun onResponse(call: Call<SessionDataModel>, response: Response<SessionDataModel>) {
                if (response.isSuccessful) {
                    Log.d("mytag", "isSuccessful=>success")
                    val sessionDataModel = response.body()

                    // Access the member property
                    val members: List<HydraMember> = sessionDataModel?.member ?: emptyList()



                    // LinkedHashSet to store unique room names while preserving insertion order
                    val uniqueRoomNames = LinkedHashSet<String>()

                    // Iterate over the list of HydraMember objects
                    for (member in members) {
                        Log.d("mytag","member room names=>"+member.room.name)
                        // Access the room name of each member and add it to the LinkedHashSet
                        member.room?.name?.let { uniqueRoomNames.add(it) }
                    }

                    // Now uniqueRoomNames contains unique room names while preserving insertion order
                    val uniqueRoomNamesList = uniqueRoomNames.toList()
                    Log.d("mytag", "Unique Room Names: $uniqueRoomNamesList")


                    // Initialize adapter with the unique room names list
// Initialize RecyclerView
                    recyclerView = findViewById(R.id.RecyclerView_Room)
                    recyclerView.layoutManager = LinearLayoutManager(this@EventActivity,LinearLayoutManager.HORIZONTAL, false)
                    // Initialize adapter with the unique room names list
                    adapter = SessionAdapter(uniqueRoomNamesList) { roomName ->
                        filterSessionsByRoom(roomName, members)
                    }
                    recyclerView.adapter = adapter


                    recyclerView_session = findViewById(R.id.RecyclerView_session)
                    recyclerView_session.layoutManager = LinearLayoutManager(this@EventActivity,LinearLayoutManager.VERTICAL, false)
                    adapter1= SessionAdapter1(members)
                    recyclerView_session.adapter = adapter1


                } else {
                    Log.d("mytag", "isSuccessful=>fail")
                    // Handle unsuccessful response
                    // You can get more details from response.errorBody() if needed
                }
            }

            override fun onFailure(call: Call<SessionDataModel>, t: Throwable) {
                // Handle failure
                // This will be called if there is a network failure or if the API call itself fails
            }
        })

    }

    private fun filterSessionsByRoom(roomName: String, members: List<HydraMember>) {
        selectedRoomName = roomName

        // Filter sessions based on the selected room and update the session adapter 1
        val filteredSessions = members.filter { it.room?.name == roomName }
        adapter1.updateSessions(filteredSessions)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }


}
