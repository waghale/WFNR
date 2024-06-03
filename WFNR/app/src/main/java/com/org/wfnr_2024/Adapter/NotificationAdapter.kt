package com.org.wfnr_2024.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Model.Notification.Data
import com.org.wfnr_2024.R

class NotificationAdapter(private val context: Context) :
    RecyclerView.Adapter<MyViewHolder>() {

    private var notifications: List<Data> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_recyclerview, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun updateNotifications(newNotifications: List<Data>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.notificationTitle)
    private val messageTextView: TextView = itemView.findViewById(R.id.notificationcontent)
    private val timeTextView: TextView = itemView.findViewById(R.id.notificationTime)


    fun bind(notification: Data) {
        titleTextView.text = notification.title
        messageTextView.text = notification.content
        // Combine date and time into a single string
        val dateTime = "${notification.date} ${notification.time}"
        timeTextView.text = dateTime
        }
}
