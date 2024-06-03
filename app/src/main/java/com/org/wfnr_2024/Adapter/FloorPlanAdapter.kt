package com.org.wfnr_2024.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Interface.Floor_Plan_CallBack
import com.org.wfnr_2024.Model.Floor_Plan_Model.Floor_Plan_data
import com.org.wfnr_2024.R

class FloorPlanAdapter(
    val floorPlanArrayList: ArrayList<Floor_Plan_data>,
    val context: Context,
    val floorPlanCallBack: Floor_Plan_CallBack
) : RecyclerView.Adapter<FloorPlanAdapter.FloorPlanViewHolder>() {

    private var selectedRoomPosition = RecyclerView.NO_POSITION
    private var initiallySelectedPosition = RecyclerView.NO_POSITION

    inner class FloorPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewFloorPlan: TextView = view.findViewById(R.id.TextView_Floor_plan)
        private val layout: LinearLayout = itemView.findViewById(R.id.layout_floor_plan)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Update selected position and notify changes
                    val previousSelectedPosition = selectedRoomPosition
                    selectedRoomPosition = position
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedRoomPosition)

                    // Notify callback
                    floorPlanCallBack.Floor_Plan_clicked(floorPlanArrayList[position], position, it)
                }
            }
        }

        fun bind(data: Floor_Plan_data, position: Int) {
            textViewFloorPlan.text = data.text

            // Set background color and text color based on selection
            if (selectedRoomPosition == position || initiallySelectedPosition == position) {
                layout.setBackgroundResource(R.drawable.meetingroom_bg)
                textViewFloorPlan.setTextColor(Color.WHITE)
            } else {
                layout.setBackgroundResource(R.drawable.event_background)
                textViewFloorPlan.setTextColor(Color.GRAY)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorPlanViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_floor_list, parent, false)
        return FloorPlanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return floorPlanArrayList.size
    }

    override fun onBindViewHolder(holder: FloorPlanViewHolder, position: Int) {
        val data = floorPlanArrayList[position]
        holder.bind(data, position)
    }

    fun setInitiallySelectedPosition(position: Int) {
        if (position >= 0 && position < floorPlanArrayList.size) {
            initiallySelectedPosition = position
            selectedRoomPosition = position
            notifyDataSetChanged() // Notify the adapter that data has changed to refresh all views
        }
    }
}
