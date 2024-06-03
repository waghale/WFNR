package com.org.wfnr_2024.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.org.wfnr_2024.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)


        Dashboard_back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }

}