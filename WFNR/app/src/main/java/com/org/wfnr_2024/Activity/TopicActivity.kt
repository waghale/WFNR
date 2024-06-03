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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Adapter.TopicAdapter
import com.org.wfnr_2024.Model.Topic.HydraMember
import com.org.wfnr_2024.Model.Topic.Topic
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class TopicActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var topicadapter :TopicAdapter
lateinit var noTopicTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        showDialog()

        // Retrieve session ID from intent extras
        // Retrieve data sent via Intent
        val sessionID = intent.getStringExtra("sessionID")
        val sessionName = intent.getStringExtra("sessionName")
        val title = intent.getStringExtra("title")

        val date = intent.getStringExtra("date")
        val roomName = intent.getStringExtra("roomName")
        val time = intent.getStringExtra("time")
        // Example usage:
        Log.d("mytag", "Session ID: $sessionID")
        Log.d("mytag", "sessionName : $sessionName")
        Log.d("mytag", "title: $title")

        Log.d("mytag", "Date: $date")
        Log.d("mytag", "Room Name: $roomName")
        Log.d("mytag", "Time: $time")


        // Find the TextView by ID
        val topicTextView = findViewById<TextView>(R.id.Topic_text)
        val topicTitleTextView = findViewById<TextView>(R.id.Topic_title1)
          noTopicTextView = findViewById<TextView>(R.id.no_topic_text_view)
        val Dashboard_back = findViewById<ImageView>(R.id.Topic_btn)

        // Display the retrieved data in the TextView
        val topicText = "$date\n$roomName ($time)"
        topicTextView.text = topicText

        val topicTitle = "$sessionName: $title"
        topicTitleTextView.text = topicTitle

        Dashboard_back.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }
        // Call API to fetch topics
        sessionID?.let { if (isInternetAvailable()) {
            fetchTopics(it)
        } else {
            showNoInternetAlert("Please connect to the internet")
        } }
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


    private fun fetchTopics(sessionID: String) {
        val topicService = RetrofitClient.apiService

        // Make API call to fetch topics
        topicService.getTopics(1, 30, sessionID).enqueue(object : Callback<Topic> {
            override fun onResponse(call: Call<Topic>, response: Response<Topic>) {
                if (response.isSuccessful) {
                    val topic = response.body()
                    Log.d("mytag", "dayData => $topic")

                    // Extract members list from topic response
                    val members: List<HydraMember> = topic?.member ?: emptyList()
                    // Sort members list by position
                    val sortedMembers = members.sortedBy { it.position }


                    // Update RecyclerView with topics data
                    updateRecyclerView(sortedMembers)
                } else {
                    // Handle unsuccessful response
                    Log.e("TopicActivity", "Failed to retrieve topics: ${response.code()}")
                    showError()
                }
            }

            override fun onFailure(call: Call<Topic>, t: Throwable) {
                // Handle network failure
                Log.e("TopicActivity", "Network error: ${t.message}")
                showError()
            }
        })
    }

    private fun updateRecyclerView(members: List<HydraMember>) {
        recyclerView = findViewById(R.id.RecyclerView_topic)
        recyclerView.layoutManager = LinearLayoutManager(this@TopicActivity)

        if (members.isNotEmpty()) {
            // Show RecyclerView and hide "No Topics Available" TextView
            recyclerView.visibility = View.VISIBLE
            noTopicTextView.visibility = View.GONE

            // Initialize and set the adapter

            topicadapter = TopicAdapter(members)
            recyclerView.adapter = topicadapter
        } else {
            // Hide RecyclerView and show "No Topics Available" TextView
            recyclerView.visibility = View.GONE
            noTopicTextView.visibility = View.VISIBLE
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, EventActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }
    private fun showError() {
        // Show "No Topics Available" TextView with error message
        noTopicTextView.text = "No Topics Available"
        recyclerView.visibility = View.GONE
        noTopicTextView.visibility = View.VISIBLE
    }
}
