package com.org.wfnr_2024.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Adapter.NotificationAdapter
import com.org.wfnr_2024.Model.Notification.Data
import com.org.wfnr_2024.Model.Notification.notification
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient1
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var recyclerView: RecyclerView

    private val TAG = "mytag"
    private val CHANNEL_ID = "notification_channel_id"
    private val NOTIFICATION_ID = 101
    private lateinit var noDataTextView: TextView


    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notidi_back = findViewById<ImageView>(R.id.Event_backbtn)

        notidi_back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize NotificationAdapter
        notificationAdapter = NotificationAdapter(this)
        recyclerView.adapter = notificationAdapter
        noDataTextView = findViewById(R.id.no_data_text_view)

        sharedPreferences = getSharedPreferences("notifications", Context.MODE_PRIVATE)

        fetchNotifications()

    }

    private fun fetchNotifications() {
        if (isNetworkAvailable()) {
            // Call API to fetch new notifications
            val call = RetrofitClient1.apiService.getNotifications()
            call.enqueue(object : retrofit2.Callback<notification> {
                override fun onResponse(call: Call<notification>, response: Response<notification>) {
                    if (response.isSuccessful) {
                        val notifications = response.body()
                        showDialog()

                        // Update RecyclerView with notifications and save to SharedPreferences
                        notifications?.let {
                            notificationAdapter.updateNotifications(it.data)
                            saveNotificationsToSharedPref(it.data)
                            handleEmptyNotifications(it.data.isEmpty())

                        }
                    } else {
                        Log.e(TAG, "Failed to fetch notifications: ${response.code()}")
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<notification>, t: Throwable) {
                    Log.e(TAG, "Error fetching notifications: ${t.message}")
                    // Handle failure
                }
            })
        } else {
            // Load notifications from SharedPreferences
            loadNotificationsFromSharedPref()
        }
    }

    private fun handleEmptyNotifications(isEmpty: Boolean) {
        if (isEmpty) {
            // Show "No notifications" text
            noDataTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Show RecyclerView and hide "No notifications" text
            noDataTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    private fun saveNotificationsToSharedPref(notifications: List<Data>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonNotifications = gson.toJson(notifications)
        editor.putString("saved_notifications", jsonNotifications)
        editor.apply()
    }

    private fun loadNotificationsFromSharedPref() {
        val savedNotificationsJson = sharedPreferences.getString("saved_notifications", null)
        if (savedNotificationsJson != null) {
            val gson = Gson()
            val notifications: List<Data> = gson.fromJson(
                savedNotificationsJson,
                Array<Data>::class.java
            ).toList()
            notificationAdapter.updateNotifications(notifications)
        } else {
            Log.d(TAG, "No notifications found in SharedPreferences")
        }
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



    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }
}