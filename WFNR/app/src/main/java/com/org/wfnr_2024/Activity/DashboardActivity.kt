// DashboardActivity.kt
package com.org.wfnr_2024.Activity

import Sharepreference_login
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.MyAdapter
import com.org.wfnr_2024.R

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var images: MutableList<Int>
    private lateinit var texts: MutableList<String>
    private lateinit var sharedPreferencesManager: Sharepreference_login

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val Dashboard_back = findViewById<ImageView>(R.id.Dashboard_back)
        sharedPreferencesManager = Sharepreference_login(this)

        val linearLayout = findViewById<LinearLayout>(R.id.grid)
        val img1 = findViewById<ImageView>(R.id.notification)
        val img2 = findViewById<ImageView>(R.id.program)


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
        val linearLayout1 = findViewById<LinearLayout>(R.id.layout)

        // Set OnClickListener for your layout
        linearLayout1.setOnClickListener {


            val isLoggedIn = sharedPreferencesManager.isUserLoggedIn()
            if (isLoggedIn) {
                // User is logged in, retrieve user data
                val userData = sharedPreferencesManager.getUserData()
                if (userData != null) {
                    // User data is available, navigate to MyWCNRSection activity
                    val intent = Intent(this@DashboardActivity, MyWCNRSection::class.java)
                    intent.putExtra("userId", userData.id)
                    intent.putExtra("userName", "${userData.title} ${userData.fname} ${userData.lname}")
                    intent.putExtra("userEmail", userData.email)
                    intent.putExtra("userMobile", userData.mobile)
                    intent.putExtra("userRegNo", userData.WFNR_reg_no)
                    intent.putExtra("userCity", userData.city)
                    intent.putExtra("userCountry", userData.country)
                    intent.putExtra("userRegType", userData.reg_type)
                    startActivity(intent)
                } else {
                    Log.e("mytag", "User data is null after login")
                }
            } else {
                // User is not logged in, navigate to LoginActivity
                val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                startActivity(intent)
            }

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
            " About Us",
        )

        // Find the LinearLayout by its ID



        adapter = MyAdapter(this, images, texts)
        recyclerView.adapter = adapter
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
}
