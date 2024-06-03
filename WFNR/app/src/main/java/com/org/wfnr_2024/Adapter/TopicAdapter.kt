package com.org.wfnr_2024.Adapter

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Model.Topic.HydraMember
import com.org.wfnr_2024.R
import java.text.SimpleDateFormat
import java.util.Locale

class TopicAdapter(private val members: List<HydraMember>) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_recyclerview, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val member = members[position]

        // Bind data to views in the ViewHolder
        holder.bind(member,holder.itemView.context)
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

        fun bind(member: HydraMember,context: Context) {
            // Update views with member data
            topicDate.text = formatTime(member.begin) + " - " + formatTime(member.end)
            title.text = member.primaryTitle ?: ""
            // Create a SpannableStringBuilder to build the styled text for speakers
            // Create a SpannableStringBuilder to build the styled text for speakers
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
                // Append speaker names with dark green color, formatted with commas
                val speakerNames = member.speakers.joinToString(", ") { "${it.person.firstName} ${it.person.lastName}" }
                val speakerNamesSpannable = SpannableString(speakerNames)
                speakerNamesSpannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.dark_green)),
                    0,
                    speakerNamesSpannable.length,
                    0
                )
                builder.append("\n") // Append new line after "Speaker :"
                builder.append(speakerNamesSpannable)
            }

            // Set click listener for notes ImageView
            notesImageView.setOnClickListener {
                showNotesPopup()
            }
            // Set the styled text to the speaker TextView
            speaker.text = builder
        }

        private fun showNotesPopup() {

        }


        private fun formatTime(timestamp: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm ", Locale.getDefault())
            val date = dateFormat.parse(timestamp)
            return timeFormat.format(date)
        }
    }
}
