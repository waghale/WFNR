package com.org.wfnr_2024.Adapter



import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.MyItineraryActivity
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.ConstantsApp.Companion.formatDate
import com.org.wfnr_2024.Utils.ConstantsApp.Companion.formatTime
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question

class MyQuestionAdapter(
    val context: Context,
    val  itineraries: List<Topic_Question>
) :RecyclerView.Adapter<MyQuestionAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TextView_ST_timeslotTitle:TextView=itemView.findViewById(R.id.TextView_ST_timeslotTitle)
        val TextView_ST_Date:TextView=itemView.findViewById(R.id.TextView_ST_Date)
        val TextView_speaker_name:TextView=itemView.findViewById(R.id.TextView_speaker_name)
        val TextView_Question:TextView=itemView.findViewById(R.id.TextView_Question)

        fun bind(data: Topic_Question, position: Int) {
            TextView_ST_timeslotTitle.text=data.topic_name

            Log.d(ConstantsApp.TAG,"date=>"+data.date)

           // val dateformated=formatDate(data.date)
            try {
               // TextView_ST_Date.text=dateformated+" "+ formatTime(data.begin)+"-"+formatTime(data.end)
                TextView_speaker_name.text=data.speaker_name
                TextView_Question.text=data.topic_question

            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_question_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itineraries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=itineraries[position]

        holder.bind(data,position)
    }
}