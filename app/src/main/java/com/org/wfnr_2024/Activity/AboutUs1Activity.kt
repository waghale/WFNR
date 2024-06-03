package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.org.wfnr_2024.R

class AboutUs1Activity : AppCompatActivity() {

    lateinit var textView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us1)

        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)
        textView=findViewById(R.id.textView_Version)

        textView.text="Version "+getAppVersion()

        Dashboard_back.setOnClickListener {
finish()
        }


    }

    private fun getAppVersion(): String {
        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Unknown"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press

        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }

}