package com.org.wfnr_2024.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.TopicActivity
import com.org.wfnr_2024.Model.SessionModel.HydraMember
import com.org.wfnr_2024.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class SessionAdapter1(  private var memberList: List<HydraMember>) : RecyclerView.Adapter<SessionAdapter1.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        // Initialize views here
      //  val sessionNameTextView: TextView = itemView.findViewById(R.id.sessiontext)
        val sessionNamename: TextView = itemView.findViewById(R.id.sessionname)

        val eventTimeTextView: TextView = itemView.findViewById(R.id.eventtime)
//        fun bind(member: HydraMember) {
////        //    sessionNameTextView.text=data.toString()
////            sessionNamename.text = member.timeslotTitle + ": " + member.title
////            eventTimeTextView.text = convertToUserTimeZone(member.begin) + " - " +convertToUserTimeZone(member.end)
//
//
//            val timeslotTitle = member.timeslotTitle ?: "Plenary"
//            sessionNamename.text = "$timeslotTitle: ${member.title}"
//            eventTimeTextView.text = convertToUserTimeZone(member.begin) + " - " + convertToUserTimeZone(member.end)
//
//        }

        init {
            itemView.findViewById<CardView>(R.id.card_session).setOnClickListener {
                val context = itemView.context
                val member = memberList[adapterPosition]

                // Launch TopicActivity with necessary data (e.g., session ID)
                val intent = Intent(context, TopicActivity::class.java)
                intent.putExtra("sessionID", member.id)
               intent.putExtra("sessionName", member.timeslotTitle ?: "Plenary")
                intent.putExtra("title", member.title)

                // Format the date to "Wed, 22 May" format
                val formattedDate = getFormattedDate(member.date)
                intent.putExtra("date", formattedDate)

                intent.putExtra("roomName", member.room.name)
                intent.putExtra("time", formatTime(member.begin) + " - " + formatTime(member.end))
                // Assuming member.id is your session ID
                context.startActivity(intent)
            }
        }
        private fun getFormattedDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString) ?: return ""

            val dayOfWeek = SimpleDateFormat("E", Locale.getDefault()).format(date)
            val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)
            val monthName = SimpleDateFormat("MMM", Locale.getDefault()).format(date)

            return "$dayOfWeek, $dayOfMonth $monthName"
        }



        fun bind(member: HydraMember) {
            val timeslotTitle = member.timeslotTitle ?: "Plenary"
            sessionNamename.text = "$timeslotTitle: ${member.title}"
            eventTimeTextView.text =formatTime(member.begin) + "-" + formatTime(member.end)
        }

        private fun formatTime(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm ", Locale.getDefault())
            val date = dateFormat.parse(timestamp)
            return timeFormat.format(date)
        }
        fun convertToUserTimeZone(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
            val userTimeZone = TimeZone.getDefault()
            dateFormat.timeZone = userTimeZone

            val date = dateFormat.parse(timestamp)
            val displayFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            return displayFormat.format(date)
        }

    }

    // Function to get day name from date string

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session1_recyclerview ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Return the number of items in your data set
        return memberList.size // Replace with your data set size
    }

    fun updateSessions(newMemberList: List<HydraMember>) {
        memberList = newMemberList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind(memberList[position])
    }
}
