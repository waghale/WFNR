package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.org.wfnr_2024.R

class WelComeNoteActivity:AppCompatActivity(), View.OnClickListener {

    lateinit var Event_backbtn:ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_note)

        Event_backbtn=findViewById(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Event_backbtn->
            {
                val intent=Intent(this,AboutUsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent=Intent(this,AboutUsActivity::class.java)
        startActivity(intent)
    }
}