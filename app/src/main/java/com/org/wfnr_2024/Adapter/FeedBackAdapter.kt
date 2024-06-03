package com.org.wfnr_2024.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.MyFeedBackActivity
import com.org.wfnr_2024.Interface.FeedBack_Callback
import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_feedback
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp

class FeedBackAdapter(
    val MenuArrayList: ArrayList<Section_feedback>,
    val context: Context,val FeedBack_Callback: FeedBack_Callback
):RecyclerView.Adapter<FeedBackAdapter.ViewHolder> (){

    private var currentData: Section_feedback? = null



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), TextWatcher {

        val TextView_feedback_adapter=itemView.findViewById<TextView>(R.id.TextView_feedback_adapter)
        val AppCompatRatingBar_feedback_adapter=itemView.findViewById<AppCompatRatingBar>(R.id.AppCompatRatingBar_feedback_adapter)
        val EditText_feedback_adapter=itemView.findViewById<EditText>(R.id.EditText_feedback_adapter)
        fun bind(data: Section_feedback, position: Int) {

            currentData=data

            TextView_feedback_adapter.text=data.title

            if(data.title.equals("Comments"))
            {
                EditText_feedback_adapter.visibility=View.VISIBLE
                AppCompatRatingBar_feedback_adapter.visibility=View.GONE
            }
            else
            {
                EditText_feedback_adapter.visibility=View.GONE
                AppCompatRatingBar_feedback_adapter.visibility=View.VISIBLE
            }



            AppCompatRatingBar_feedback_adapter.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                // Handle the rating value
                val rating1=rating

                    data.rating= rating1

                FeedBack_Callback!!.FeedBack_rating(data,position)
            }

            EditText_feedback_adapter.addTextChangedListener(this)


        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
          Log.d(ConstantsApp.TAG,""+s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d(ConstantsApp.TAG,""+s.toString())
        }

        override fun afterTextChanged(s: Editable?) {

            currentData!!.comments=s.toString()
            FeedBack_Callback!!.FeedBack_rating(currentData!!,position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feedback_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return MenuArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=MenuArrayList[position]

        holder.bind(data,position)
    }
}