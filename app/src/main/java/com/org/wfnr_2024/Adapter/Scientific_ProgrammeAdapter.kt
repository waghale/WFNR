package com.org.wfnr_2024.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Model.Scientific_programme_commitee_model
import com.org.wfnr_2024.R

class Scientific_ProgrammeAdapter(
    context: Context,
    Organizing_commiteeArrayList: ArrayList<Scientific_programme_commitee_model>
):RecyclerView.Adapter<Scientific_ProgrammeAdapter.ViewHolder>() {

    var context: Context
    var  Organizing_commiteeArrayList: ArrayList<Scientific_programme_commitee_model>?=null

    init {
        this.context=context
        this.Organizing_commiteeArrayList=Organizing_commiteeArrayList
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Scientific_ProgrammeAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_organizing_committee, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Scientific_ProgrammeAdapter.ViewHolder,
        position: Int
    ) {
        val model = Organizing_commiteeArrayList!![position]
        holder.bind(model,holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return Organizing_commiteeArrayList!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val TextView_name: TextView = itemView.findViewById(R.id.TextView_name)
        val TextView_title: TextView = itemView.findViewById(R.id.TextView_title)
        val TextView_description: TextView = itemView.findViewById(R.id.TextView_description)
        val ImageView_Oraganizing_commitee: ImageView = itemView.findViewById(R.id.ImageView_Oraganizing_commitee)

        fun bind(model: Scientific_programme_commitee_model, adapterPosition: Int) {

            TextView_name.text=model.name
            TextView_description.text=model.description
            ImageView_Oraganizing_commitee.setImageResource(model.image)
            TextView_title.text=model.title

            if (model.title.equals(""))
            {
                TextView_title.visibility=View.GONE
            }
            else
            {
                TextView_title.visibility=View.VISIBLE
            }

        }
    }
}