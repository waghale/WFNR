package com.org.wfnr_2024.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.org.wfnr_2024.R

class MyWCNRSection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wcnrsection)

        // Retrieve user data from intent extras
        val userId = intent.getIntExtra("userId", 0) // Example: Int
        val userName = intent.getStringExtra("userName") // Example: String
        val userEmail = intent.getStringExtra("userEmail") // Example: String
        val userMobile = intent.getStringExtra("userMobile") // Example: String
        val userRegNo = intent.getStringExtra("userRegNo") // Example: String
        val userCity = intent.getStringExtra("userCity") // Example: String
        val userCountry = intent.getStringExtra("userCountry") // Example: String
        val userRegType = intent.getStringExtra("userRegType") // Example: String

        // Log or use the received user data as needed
        Log.d("mytag", "User ID: $userId")
        Log.d("mytag", "User Name: $userName")
        Log.d("mytag", "User Email: $userEmail")
        Log.d("mytag", "User Mobile: $userMobile")
        Log.d("mytag", "User Registration No: $userRegNo")
        Log.d("mytag", "User City: $userCity")
        Log.d("mytag", "User Country: $userCountry")
        Log.d("mytag", "User Registration Type: $userRegType")
        // Find the ImageView
        val myProfileImageView = findViewById<ImageView>(R.id.myprofile)

        myProfileImageView.setOnClickListener {
            val intent = Intent(this, WCNR_MyProfile::class.java).apply {
                putExtra("userId", userId)
                putExtra("userName", userName)
                putExtra("userEmail", userEmail)
                putExtra("userMobile", userMobile)
                putExtra("userRegNo", userRegNo)
                putExtra("userCity", userCity)
                putExtra("userCountry", userCountry)
                putExtra("userRegType", userRegType)
            }
            startActivity(intent)
        }
        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)
        if (intent.hasExtra("successMessage")) {
            val successMessage = intent.getStringExtra("successMessage")
            if (!successMessage.isNullOrEmpty()) {
                // Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()
                showCustomToast1(successMessage)

            }
        }
        Dashboard_back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCustomToast1(message: String) {
        // Inflate the custom toast layout
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.login_sucess_toast, null)

        // Set the message in the custom toast layout
        val customToastMessage = layout.findViewById<TextView>(R.id.CustomToastnotificationTitle)
        val imgCancel: ImageView = layout.findViewById(R.id.imgCancel)

        customToastMessage.text = message

        // Create and show the custom toast with bottom gravity
        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.BOTTOM, 0, 32)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        // Set click listener on imgCancel to dismiss the toast
        imgCancel.setOnClickListener {
            toast.cancel()
        }

        toast.show()
        // Delay dismissing the toast after a certain duration
        Handler().postDelayed({
            toast.cancel()
        }, 1000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()

    }
}