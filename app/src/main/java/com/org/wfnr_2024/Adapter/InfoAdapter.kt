package com.org.wfnr_2024.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.AboutUsActivity
import com.org.wfnr_2024.Interface.InfoCallBack
import com.org.wfnr_2024.Model.InfoModel.InfoData
import com.org.wfnr_2024.R

class InfoAdapter(val InfoArrayList: ArrayList<InfoData>,
                  val context: Context,
    val InfoCallBack: InfoCallBack
) :RecyclerView.Adapter<InfoAdapter.ViewHolder>() {



    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val TextView_info_Title=view.findViewById<TextView>(R.id.TextView_info_Title)


        fun bind(data: InfoData, position: Int) {

            TextView_info_Title.text=data.text

            TextView_info_Title.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                    InfoCallBack.Info_Menu_Clicked(position,data, v!!)
                }

            })



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_info_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return InfoArrayList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val data=InfoArrayList[position]

        holder.bind(data, position)
    }


}