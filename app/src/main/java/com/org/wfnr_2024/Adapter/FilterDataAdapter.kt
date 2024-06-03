package com.org.wfnr_2024.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Interface.GetFilterClickData
import com.org.wfnr_2024.R
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.DisplayItem
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local

class FilterDataAdapter(val filterlist: List<DisplayItem>,val GetFilterClickData: GetFilterClickData) :RecyclerView.Adapter<FilterDataAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val TextView_name:TextView=view.findViewById(R.id.TextView_name)
        val LinearLayout_filterData:LinearLayout=view.findViewById(R.id.LinearLayout_filterData)
        fun bind(data: DisplayItem, position: Int) {

           /* if (data.title.equals(null))
            {
                TextView_name.text=data.first_name+" "+data.last_name
            }
            else{
                TextView_name.text=data.title+" "+data.first_name+" "+data.last_name
            }*/

            TextView_name.text=data.name

            LinearLayout_filterData.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                    GetFilterClickData.getFilterClickedData(data,position,v!!)
                }

            })

            TextView_name.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                    GetFilterClickData.getFilterClickedData(data,position,v!!)
                }

            })


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=filterlist[position]

        holder.bind(data,position)
    }
}