package com.org.wfnr_2024.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.R

class SessionAdapter(val uniqueRoomNamesList: List<String>,private val roomClickListener: (String) -> Unit) : RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    private var selectedRoomPosition = 0
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Initialize views here
        val sessionNameTextView: TextView = itemView.findViewById(R.id.sessiontext)
        val layout: LinearLayout = itemView.findViewById(R.id.layout)

        fun bind(data: String, position: Int) {
            sessionNameTextView.text = data.toString()

            // Set click listener
            itemView.setOnClickListener {
                roomClickListener.invoke(data)
                notifyItemChanged(selectedRoomPosition)
                selectedRoomPosition = adapterPosition
                notifyItemChanged(selectedRoomPosition)
            }

// Set click listener

//            // Set background color and text color based on selection
//            if (selectedRoomPosition == position) {
//                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_green))
//                sessionNameTextView.setTextColor(Color.WHITE)
//            } else {
//                itemView.setBackgroundColor(Color.WHITE)
//                sessionNameTextView.setTextColor(Color.GRAY)
//            }
            }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Return the number of items in your data set
        return uniqueRoomNamesList.size // Replace with your data set size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = uniqueRoomNamesList[position]

        holder.bind(data, position)

        // Set the first room as selected by default and filter its data
        if (position == selectedRoomPosition) {
            holder.layout.setBackgroundResource(R.drawable.meetingroom_bg)
            holder.sessionNameTextView.setTextColor(Color.WHITE)
            // Pass the selected room to the clzick listener
            roomClickListener.invoke(data)
        } else {
            holder.layout.setBackgroundResource(R.drawable.event_background)
            holder.sessionNameTextView.setTextColor(Color.GRAY)
        }
    }

}
