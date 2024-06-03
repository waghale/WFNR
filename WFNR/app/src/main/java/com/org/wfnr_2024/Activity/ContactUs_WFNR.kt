package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.org.wfnr_2024.R

class ContactUs_WFNR : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_wfnr)


        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)

        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewPhoneNumber = findViewById<TextView>(R.id.textViewPhoneNumber)
        val textViewWebsite = findViewById<TextView>(R.id.textViewWebsite)

        // Set ClickableSpan for email
        setClickableSpan(textViewEmail, "traceymole@wfnr.co.uk", ClickableEmailSpan())

        // Set ClickableSpan for phone number
        setClickableSpan(textViewPhoneNumber, "+44 (0)7548342642", ClickablePhoneSpan())

        // Set ClickableSpan for website
        setClickableSpan(textViewWebsite, "www.wfnr.co.uk", ClickableWebsiteSpan())



        Dashboard_back.setOnClickListener {
            finish()
        }
    }

        private fun setClickableSpan(textView: TextView, text: String, clickableSpan: ClickableSpan) {
            val spannableString = SpannableString(textView.text)
            val start = spannableString.indexOf(text)
            val end = start + text.length
            spannableString.setSpan(clickableSpan, start, end, 0)
            textView.text = spannableString
            textView.movementMethod = TextViewLinkHandler() // Enable link handling in TextView
        }

        private inner class ClickableEmailSpan : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:traceymole@wfnr.co.uk")
                startActivity(intent)
            }
        }

        private inner class ClickablePhoneSpan : ClickableSpan() {
            override fun onClick(widget: View) {
                val phoneNumber = "+447548342642" // Replace with the actual phone number
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
                startActivity(intent)
            }
        }

        private inner class ClickableWebsiteSpan : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wfnr.co.uk"))
                startActivity(intent)
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press

        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }
}