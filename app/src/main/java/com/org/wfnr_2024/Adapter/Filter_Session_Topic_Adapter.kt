package com.org.wfnr_2024.Adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.EventActivity
import com.org.wfnr_2024.Model.Chair_Speaker_List.ChairSpeakerList
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp.Companion.formatTime

class Filter_Session_Topic_Adapter(
    val context: Context,
    val distinctList: List<ChairSpeakerList>
) :RecyclerView.Adapter<Filter_Session_Topic_Adapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val TextView_ST_timeslotTitle:TextView=view.findViewById(R.id.TextView_ST_timeslotTitle)
        val TextView_ST_Title:TextView=view.findViewById(R.id.TextView_ST_Title)
        val TextView_ST_room:TextView=view.findViewById(R.id.TextView_ST_room)
        val TextView_ST_date:TextView=view.findViewById(R.id.TextView_ST_date)
        val TextView_ST_time:TextView=view.findViewById(R.id.TextView_ST_time)
        fun bind(data: ChairSpeakerList, position: Int) {

            if (data.type.equals("Chair"))
            {

                TextView_ST_timeslotTitle.setTextColor(ContextCompat.getColor(context, R.color.green))
                TextView_ST_timeslotTitle.setTypeface(TextView_ST_timeslotTitle.typeface, Typeface.BOLD)
                TextView_ST_Title.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            else
            {
                TextView_ST_timeslotTitle.setTextColor(ContextCompat.getColor(context, R.color.dark_green))
                TextView_ST_timeslotTitle.setTypeface(TextView_ST_timeslotTitle.typeface, Typeface.BOLD)
                TextView_ST_Title.setTextColor(ContextCompat.getColor(context, R.color.dark_green))
            }

            TextView_ST_timeslotTitle.text=data.timeslotTitle
            TextView_ST_Title.text=data.sessio_topic_title
            TextView_ST_room.text=data.room_name
            TextView_ST_date.text=data.date
            TextView_ST_time.text=formatTime(data.start_time!!) + " - " + formatTime(data.end_time!!)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_session_topic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return distinctList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=distinctList[position]

        holder.bind(data,position)
    }


}