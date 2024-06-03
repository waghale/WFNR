package com.org.wfnr_2024.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Activity.MyWCNRSection
import com.org.wfnr_2024.Interface.MenuCallBack
import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_Menu
import com.org.wfnr_2024.R

class MenuAdapter(val MenuArrayList: ArrayList<Section_Menu>, val context: Context,
    val MenuCallBack: MenuCallBack
) :RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TextView_menu:TextView=itemView.findViewById(R.id.TextView_menu)
        val ImageView_menu:ImageView=itemView.findViewById(R.id.ImageView_menu)
        fun bind(data: Section_Menu, position: Int) {

            TextView_menu.text=data.label

            ImageView_menu.setImageResource(data.image!!)



            TextView_menu.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                    MenuCallBack.menuClicked(data,position,v!!)
                }

            })



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
       val data=MenuArrayList[position]

        holder.bind(data,position)
    }

    override fun getItemCount(): Int {
       return MenuArrayList.size
    }
}