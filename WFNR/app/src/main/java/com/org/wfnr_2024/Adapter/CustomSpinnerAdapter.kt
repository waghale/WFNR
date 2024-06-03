package com.org.wfnr_2024.Adapter

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.org.wfnr_2024.R
import java.text.SimpleDateFormat
import java.util.Locale

class CustomSpinnerAdapter(context: Context, daysList: List<String>) :
    ArrayAdapter<String>(context, 0, daysList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.spinner_item, parent, false)

        val dayTextView: TextView = view.findViewById(R.id.dayTextView)
        val currentItem = getItem(position)

//
        currentItem?.let {
            // Parse the date string and format it
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy ", Locale.getDefault())
            val date = inputFormat.parse(it)
            val formattedDate = outputFormat.format(date)

            // Set the formatted date to the dateTextView
            dayTextView.text = formattedDate

            // Set the image resource for the ImageView (assuming all items have the same image)
        }
        // Set the background color of the spinner item
        if (isDarkModeEnabled()) {
            view.setBackgroundResource(R.color.white) // Set black color for dark mode
        } else {
            // Set default background color for light mode (if needed)
            // view.setBackgroundResource(R.color.white)
        }


        //    dayTextView.text = currentItem?.date // Adjust property name if needed

        return view
    }

    private fun isDarkModeEnabled(): Boolean {
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

}
