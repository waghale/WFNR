package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.org.wfnr_2024.R

class WCNR_MyProfile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wcnr_my_profile)


        val Dashboard_back = findViewById<ImageView>(R.id.Dashboard_back)
        Dashboard_back.setOnClickListener {
finish()
        }

        // Retrieve user data from intent extras
        val userId = intent.getIntExtra("userId", 0)
        val userName = intent.getStringExtra("userName")
        val userEmail = intent.getStringExtra("userEmail")
        val userMobile = intent.getStringExtra("userMobile")
        val userRegNo = intent.getStringExtra("userRegNo")
        val userCity = intent.getStringExtra("userCity")
        val userCountry = intent.getStringExtra("userCountry")
        val userRegType = intent.getStringExtra("userRegType")

        // Display user data in TextViews
        findViewById<TextView>(R.id.textViewUserName).text = "User ID: $userId"
        findViewById<TextView>(R.id.textViewUserName).text = "$userName"
        findViewById<TextView>(R.id.textViewUserregno).text = "WCNR Registration No: $userRegNo"
        findViewById<TextView>(R.id.textViewUserEmail).text = "Email: $userEmail"
        findViewById<TextView>(R.id.textViewUserCity).text = "City: $userCity"
        findViewById<TextView>(R.id.textViewUserCountry).text = "Country: $userCountry"


    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }
}
