package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.InfoAdapter
import com.org.wfnr_2024.Interface.InfoCallBack
import com.org.wfnr_2024.Model.InfoModel.InfoData
import com.org.wfnr_2024.R

class AboutUsActivity : AppCompatActivity(), InfoCallBack {
    @SuppressLint("MissingInflatedId")


     var InfoArrayList:ArrayList<InfoData>?=null

    lateinit var RecyclerView_info:RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us2)

        val Dashboard_back = findViewById<ImageView>(R.id.Event_backbtn)

        RecyclerView_info=findViewById(R.id.RecyclerView_info)



        InfoArrayList= ArrayList()

        InfoArrayList!!.add(InfoData("Welcome Note"))
        InfoArrayList!!.add(InfoData("Useful Info"))
        InfoArrayList!!.add(InfoData("Meeting Room Floor Plan"))

        RecyclerView_info.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = InfoAdapter(InfoArrayList!!,this, this)
        RecyclerView_info.adapter = adapter


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

    override fun Info_Menu_Clicked(position: Int, data: InfoData, view: View) {
        when(data.text)
        {
            "Welcome Note"->
            {
                val intent=Intent(this,WelComeNoteActivity::class.java)
                startActivity(intent)

            }
            "Useful Info"->
            {
                val intent=Intent(this,UsefulInfoActivity::class.java)
                startActivity(intent)
            }
            "Meeting Room Floor Plan"->
            {
                val intent=Intent(this,Meeting_Room_Floor_Plan_Activity::class.java)
                startActivity(intent)
            }
            else->
            {

            }

        }
    }

}