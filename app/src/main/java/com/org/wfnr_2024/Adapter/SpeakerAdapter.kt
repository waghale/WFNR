package com.org.wfnr_2024.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.org.wfnr_2024.R

class SpeakerAdapter(context: Context, private val speakerNames: List<String>) :
    ArrayAdapter<String>(context, R.layout.list_item_layout, speakerNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        }

        // Get reference to TextView in list item layout
        val speakerNameTextView = itemView!!.findViewById<TextView>(R.id.speakerTextView)

        // Set the speaker name to the TextView
        speakerNameTextView.text = speakerNames[position]

        return itemView
    }
}
