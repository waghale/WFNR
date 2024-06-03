package com.org.wfnr_2024.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Interface.Get_Session_CallBack
import com.org.wfnr_2024.R
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import java.text.SimpleDateFormat
import java.util.*

class SessionAdapter1(private var memberList: List<Session>,val Get_Session_CallBack: Get_Session_CallBack) :
    RecyclerView.Adapter<SessionAdapter1.ViewHolder>() {




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize views here
        private val sessionNameTextView: TextView = itemView.findViewById(R.id.sessionname)
        private val eventTimeTextView: TextView = itemView.findViewById(R.id.eventtime)
        private val cardView: CardView = itemView.findViewById(R.id.card_session)

        fun bind(member: Session, position: Int) {
            val timeslotTitle = member.timeslotTitle ?: "Plenary"
            sessionNameTextView.text = "$timeslotTitle: ${member.title}"
            eventTimeTextView.text = formatTime(member.begin!!) + " - " + formatTime(member.end!!)

            cardView.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                    Get_Session_CallBack.getSessionCall(member,position,v!!)
                }

            })

           /* cardView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, TopicActivity::class.java).apply {
                    putExtra("sessionID", member.id)
                    putExtra("sessionName", member.timeslotTitle ?: "Plenary")
                    putExtra("title", member.title)

                    val formattedDate = getFormattedDate(member.date)
                    putExtra("date", formattedDate)

                    member.room?.let { room ->
                        putExtra("roomName", room.name)
                    }

                    putExtra("time", formatTime(member.begin) + " - " + formatTime(member.end))
                }
                context.startActivity(intent)
            }*/
        }

        private fun getFormattedDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString) ?: return ""

            val dayOfWeek = SimpleDateFormat("E", Locale.getDefault()).format(date)
            val dayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)
            val monthName = SimpleDateFormat("MMM", Locale.getDefault()).format(date)

            return "$dayOfWeek, $dayOfMonth $monthName"
        }

        private fun formatTime(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = dateFormat.parse(timestamp) ?: return ""
            return timeFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session1_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("mytag",""+memberList.size)
        return memberList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
      //  setHasStableIds(true);
        holder.bind(memberList[position],position)
    }

    // Function to update the dataset
    fun updateSessions(newMemberList: List<Session>) {
        memberList = newMemberList
        notifyDataSetChanged()
    }


    // Function to set the initially selected position

}
