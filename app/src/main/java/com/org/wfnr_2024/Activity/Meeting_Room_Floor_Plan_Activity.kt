package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.org.wfnr_2024.Adapter.FloorPlanAdapter
import com.org.wfnr_2024.Interface.Floor_Plan_CallBack
import com.org.wfnr_2024.Model.Floor_Plan_Model.Floor_Plan_data

import com.org.wfnr_2024.R

class Meeting_Room_Floor_Plan_Activity:AppCompatActivity(),OnClickListener, Floor_Plan_CallBack {

    var FloorPlanArrayList:ArrayList<Floor_Plan_data>?=null

    lateinit var adapter:FloorPlanAdapter

    lateinit var Event_backbtn: ImageView
    lateinit var ImageView_floor_plan:PhotoView
    lateinit var RecyclerView_FloorPlan:RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_room_floor_plan)

        Event_backbtn=findViewById(R.id.Event_backbtn)
        ImageView_floor_plan=findViewById(R.id.ImageView_floor_plan)
        RecyclerView_FloorPlan=findViewById(R.id.RecyclerView_FloorPlan)

        ImageView_floor_plan.scaleType = ImageView.ScaleType.FIT_CENTER


        /*// Enable zooming programmatically
        ImageView_floor_plan.minimumScale = 1.0f // Default value
        ImageView_floor_plan.mediumScale = 2.0f
        ImageView_floor_plan.maximumScale = 4.0f*/

        // Optionally set the default zoom scale
        ImageView_floor_plan.setScale(ImageView_floor_plan.minimumScale, true)

        ImageView_floor_plan.setZoomable(true)

        Event_backbtn.setOnClickListener(this)

        FloorPlanArrayList= ArrayList()

        FloorPlanArrayList!!.add(Floor_Plan_data("Industry",R.drawable.industry_floor_plan))
        FloorPlanArrayList!!.add(Floor_Plan_data("Lobby",R.drawable.lobby_floor_plan))
        FloorPlanArrayList!!.add(Floor_Plan_data("Meeting Room Level",R.drawable.meeting_floor_plan))

        RecyclerView_FloorPlan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = FloorPlanAdapter(FloorPlanArrayList!!,this,this)
        RecyclerView_FloorPlan.adapter = adapter

        adapter.notifyDataSetChanged()

        adapter.setInitiallySelectedPosition(0)

        ImageView_floor_plan.setImageResource(R.drawable.industry_floor_plan)


    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Event_backbtn->
            {
                val intent= Intent(this,AboutUsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this,AboutUsActivity::class.java)
        startActivity(intent)
    }

    override fun Floor_Plan_clicked(data: Floor_Plan_data, position: Int, view: View) {

        adapter.setInitiallySelectedPosition(position)
        adapter.notifyDataSetChanged()
        ImageView_floor_plan.setImageResource(data.image)
    }

}
