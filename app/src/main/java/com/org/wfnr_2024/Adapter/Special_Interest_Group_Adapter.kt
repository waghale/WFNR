package org.impactindiafoundation.wfnr.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.R
import org.impactindiafoundation.wfnr.CallBack.SIGNext
import org.impactindiafoundation.wfnr.CallBack.SendMailCallBack
import org.impactindiafoundation.wfnr.Model.Special_Interest_Group_Model



class Special_Interest_Group_Adapter(
    val context: Context,
    val specialInterestGroupArraylist: ArrayList<Special_Interest_Group_Model>,
    val SIGNext: SIGNext,
    val SendMailCallBack: SendMailCallBack
) :RecyclerView.Adapter<Special_Interest_Group_Adapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TextView_title: TextView = itemView.findViewById(R.id.TextView_title)
        val TextView_contact: TextView = itemView.findViewById(R.id.TextView_contact)
        val ImageView_next: ImageView = itemView.findViewById(R.id.ImageView_next)
        val RecyclerView_contacts: RecyclerView = itemView.findViewById(R.id.RecyclerView_contacts)
        fun bind(model: Special_Interest_Group_Model, adapterPosition: Int) {


            TextView_title.text=model.title

            val size = model.email?.size ?: 0
            if (size == 0) {
                TextView_contact.visibility = View.GONE
                RecyclerView_contacts.visibility = View.GONE
            }
            else
            {
                TextView_contact.visibility = View.VISIBLE
                RecyclerView_contacts.visibility = View.VISIBLE

                val adapter1 = Email_Adapter(context, model.email!!, model.title!!,SendMailCallBack)
                val layoutManager1 = LinearLayoutManager(context)
                layoutManager1.orientation = LinearLayoutManager.VERTICAL // or HORIZONTAL
                RecyclerView_contacts.layoutManager = layoutManager1

                RecyclerView_contacts.adapter = adapter1

                if (size == 1) {
                    TextView_contact.text = "Contact"
                } else {
                    TextView_contact.text = "Contacts"
                }
            }

            ImageView_next.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {
                    SIGNext.OnNextPage(model,adapterPosition,v!!)
                }

            })

            TextView_title.setOnClickListener(object:View.OnClickListener
            {
                override fun onClick(v: View?) {
                    SIGNext.OnNextPage(model,adapterPosition,v!!)
                }

            })






        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_special_interest_group, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specialInterestGroupArraylist.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val model=specialInterestGroupArraylist[position]
        holder.bind(model,holder.adapterPosition)
    }
}