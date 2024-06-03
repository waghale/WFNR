package com.org.wfnr_2024.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Room
import com.org.wfnr_2024.R

class SessionAdapter(
    private val uniqueRoomNamesList: List<Room>,
    private val roomClickListener: (String) -> Unit
) : RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    private var selectedRoomPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views here
        val sessionNameTextView: TextView = itemView.findViewById(R.id.sessiontext)
        val layout: LinearLayout = itemView.findViewById(R.id.layout)

        fun bind(roomName: String, position: Int) {
            sessionNameTextView.text = roomName

            // Set click listener
            itemView.setOnClickListener {
                if (selectedRoomPosition != adapterPosition) {
                    notifyItemChanged(selectedRoomPosition)
                    selectedRoomPosition = adapterPosition
                    notifyItemChanged(selectedRoomPosition)

                    // Pass the selected room name to the click listener
                    roomClickListener.invoke(roomName)
                }
            }

            // Set background color and text color based on selection
            if (selectedRoomPosition == position) {
                layout.setBackgroundResource(R.drawable.meetingroom_bg)
                sessionNameTextView.setTextColor(Color.WHITE)
            } else {
                layout.setBackgroundResource(R.drawable.event_background)
                sessionNameTextView.setTextColor(Color.GRAY)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return uniqueRoomNamesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roomName = uniqueRoomNamesList[position].name
        holder.bind(roomName, position)

        // Set the first room as selected by default
        if (position == 0 && selectedRoomPosition == RecyclerView.NO_POSITION) {
            selectedRoomPosition = 0 // Select the first item
            holder.layout.setBackgroundResource(R.drawable.meetingroom_bg)
            holder.sessionNameTextView.setTextColor(Color.WHITE)
            roomClickListener.invoke(roomName) // Invoke listener with the selected room name
        }
    }
}
