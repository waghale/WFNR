// MyAdapter.kt
package com.org.wfnr_2024.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.AboutUsActivity
import com.org.wfnr_2024.Activity.CommitteActiivty
import com.org.wfnr_2024.Activity.ContactUsActivity
import com.org.wfnr_2024.R

class MyAdapter(private val mContext: Context, private val mImages: List<Int>, private val mTexts: List<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageId = mImages[position]
        val text = mTexts[position]
        holder.imageView.setImageResource(imageId)
        holder.textView.text = text

        // Set click listener for the item
        holder.imageView.setOnClickListener {
            Log.d("mytag", "image view click")

            // Retrieve the text associated with the item
            val itemText = text.trim()

            // Check the text and start the corresponding activity
            when (itemText) {
                "Committee" -> {
                    val intent = Intent(mContext, CommitteActiivty::class.java)
                    mContext.startActivity(intent)
                }
                "About Us" -> {
                    val intent = Intent(mContext, AboutUsActivity::class.java)
                    mContext.startActivity(intent)
                }
                "Contact Us" -> {
                    val intent = Intent(mContext, ContactUsActivity::class.java)
                    mContext.startActivity(intent)
                }
                // Add more conditions for other items if needed
                else -> {
                    // Handle other cases if necessary
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mImages.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
