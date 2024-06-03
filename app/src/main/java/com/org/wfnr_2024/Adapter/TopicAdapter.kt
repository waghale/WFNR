package com.org.wfnr_2024.Adapter

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Interface.Topic_CallBack
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import java.text.SimpleDateFormat
import java.util.Locale

class TopicAdapter(private val members: List<Session.ProgramPoint>, private val Topic_CallBack:Topic_CallBack) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_recyclerview, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val member = members[position]

        // Bind data to views in the ViewHolder
        holder.bind(member,holder.itemView.context,Topic_CallBack,position)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    // ViewHolder class to hold references to views
    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicDate: TextView = itemView.findViewById(R.id.topicdate)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val speaker: TextView = itemView.findViewById(R.id.speaker)
        private val notesImageView: ImageView = itemView.findViewById(R.id.notes)
       private val votingImageView: ImageView = itemView.findViewById(R.id.voting)
      private val questionImageView: ImageView = itemView.findViewById(R.id.question)
        private val feedbackImageView: ImageView = itemView.findViewById(R.id.feedback)
       private val favouriteImageView: ImageView = itemView.findViewById(R.id.favourite)

        fun bind(
            member: Session.ProgramPoint,
            context: Context,
            Topic_CallBack: Topic_CallBack,
            position: Int
        ) {
            // Update views with member data
            topicDate.text = formatTime(member.begin!!) + " - " + formatTime(member.end!!)
            title.text = member.primaryTitle ?: ""

           // Log.d(ConstantsApp.TAG,"convertGMT7ToLocalTime=>"+ConstantsApp.convertGMT7ToLocalTime(member.begin))

            // Create a SpannableStringBuilder to build the styled text for speakers and titles
            val builder = SpannableStringBuilder()

            // Append "Speaker :" with green color
            val speakerLabel = SpannableString("Speaker : ")
            speakerLabel.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)),
                0,
                speakerLabel.length,
                0
            )
            builder.append(speakerLabel)

            // Check if speakers are available
            if (member.speakers.isNotEmpty()) {
                // Reverse the list of speakers
                val reversedSpeakers = member.speakers.reversed()

                for ((index, speaker) in reversedSpeakers.withIndex()) {
                    // Append speaker name with dark green color
                    val fullName = "${speaker.person!!.firstName} ${speaker.person.lastName}"
                    val fullNameSpannable = SpannableString(fullName)
                    fullNameSpannable.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.dark_green)),
                        0,
                        fullNameSpannable.length,
                        0
                    )
                    builder.append("\n") // Append new line after each speaker

                    // Append speaker's title (if available) with a different style
                    if (!speaker.person.title.isNullOrBlank()) {
                        val speakerInfo = "${speaker.person.title} $fullName"
                        val speakerInfoSpannable = SpannableString(speakerInfo)
                        speakerInfoSpannable.setSpan(
                            StyleSpan(android.graphics.Typeface.BOLD),
                            0,
                            speakerInfoSpannable.length,
                            0
                        )
                        builder.append(speakerInfoSpannable)
                    } else {
                        builder.append(fullNameSpannable)
                    }
                }
            }

//            // Set click listener for notes ImageView
//            notesImageView.setOnClickListener {
//                showNotesPopup()
//            }

            // Set the styled text to the speaker TextView
            speaker.text = builder

            feedbackImageView.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {

                    Topic_CallBack.topic_clicked(member,position, v!!,"feedback")

                }

            })

            questionImageView.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {

                    Topic_CallBack.topic_clicked(member,position, v!!,"question")

                }

            })

            votingImageView.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {

                    Topic_CallBack.topic_clicked(member,position, v!!,"voting")

                }

            })
            notesImageView.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {

                    Topic_CallBack.topic_clicked(member,position, v!!,"notes")

                }

            })

            favouriteImageView.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {

                    Topic_CallBack.topic_clicked(member,position, v!!,"favourite")

                }

            })



        }




        private fun formatTime(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm ", Locale.getDefault())
            val date = dateFormat.parse(timestamp)
            return timeFormat.format(date)
        }
    }
}
