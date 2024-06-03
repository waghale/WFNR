package com.org.wfnr_2024.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.org.wfnr_2024.R

class ContactUsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)



        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)


        Dashboard_back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }


        val websiteTextView: TextView = findViewById(R.id.website)
        val websiteTextView1: TextView = findViewById(R.id.textViewWebsite)



        // Define the URLs for each clickable TextView
        val websiteUrl1 = "http://wcnr@conventus.de"
        val websiteUrl2 = "http:/wcnr@conventus.de"

        // Set up ClickableSpan for the first TextView
        setClickableSpan(websiteTextView, "wcnr@conventus.de", websiteUrl1)

        // Set up ClickableSpan for the second TextView
        setClickableSpan(websiteTextView1, "wcnr@conventus.de", websiteUrl2)
        }


    private fun setClickableSpan(textView: TextView, text: String, url: String) {
        // Create a SpannableString with the specified text
        val spannableString = SpannableString(text)

        // Create a clickable span for the specified URL
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click by opening the URL
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }

        // Set the clickable span to the SpannableString
        spannableString.setSpan(clickableSpan, 0, text.length, 0)

        // Set the SpannableString to the TextView
        textView.text = spannableString

        // Make the TextView clickable and enable the movement method
        textView.isClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }

}