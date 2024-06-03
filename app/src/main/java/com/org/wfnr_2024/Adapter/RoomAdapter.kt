package com.org.wfnr_2024.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Interface.GetScientificProgramCallBack
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp

class RoomAdapter(
    private val roomList: List<GET_DAYS_RESPONSE.HydraMember.RoomOrder>,
    private val getScientificProgramCallBack: GetScientificProgramCallBack
) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private var selectedRoomPosition = RecyclerView.NO_POSITION
    private var initiallySelectedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sessionNameTextView: TextView = itemView.findViewById(R.id.sessiontext)
        private val layout: LinearLayout = itemView.findViewById(R.id.layout)

        init {


            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && position != selectedRoomPosition) {
                    // Update selected position and notify data set changed
                    val previousSelectedPosition = selectedRoomPosition
                    selectedRoomPosition = position
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedRoomPosition)


                    // Pass the selected room name to the callback
                    val roomName = roomList[position]
                    getScientificProgramCallBack.getScientificProgram(roomName, position, itemView)
                }
            }
        }

        fun bind(roomName: GET_DAYS_RESPONSE.HydraMember.RoomOrder, position: Int) {
            sessionNameTextView.text = roomName.name

            // Set background color and text color based on selection
            if (selectedRoomPosition == position) {
                layout.setBackgroundResource(R.drawable.meetingroom_bg)
                sessionNameTextView.setTextColor(Color.WHITE)
            } else {
                layout.setBackgroundResource(R.drawable.event_background)
                sessionNameTextView.setTextColor(Color.GRAY)
            }

            // Add this line to ensure that the background changes are reflected immediately
            itemView.isSelected = selectedRoomPosition == position || initiallySelectedPosition == position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = roomList[position]
        holder.bind(data, position)
    }

    fun setInitiallySelectedPosition(position: Int) {
        if (position >= 0 && position < roomList.size) {
            // Update initially selected position
            initiallySelectedPosition = position
            // Update selectedRoomPosition and notify item changed
            selectedRoomPosition = position
            notifyItemChanged(position)
        }
    }
}
