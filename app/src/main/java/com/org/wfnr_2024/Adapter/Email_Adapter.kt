package org.impactindiafoundation.wfnr.Adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.R
import org.impactindiafoundation.wfnr.CallBack.SendMailCallBack
import org.impactindiafoundation.wfnr.Model.Email


class Email_Adapter(val context: Context, val email: ArrayList<Email>,val topic:String,val SendMailCallBack: SendMailCallBack) : RecyclerView.Adapter<Email_Adapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TextView_contact_name: TextView = itemView.findViewById(R.id.TextView_contact_name)
        val TextView_contact_email: TextView = itemView.findViewById(R.id.TextView_contact_email)
        fun bind(model: Email, adapterPosition: Int) {
            TextView_contact_name.text=model.name
            //TextView_contact_email.text=model.email

            var emailAddress=model.email


            // Create a SpannableString with the email address
            val spannableString = SpannableString(emailAddress)

            // Underline the email address
            spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the SpannableString to the TextView
            TextView_contact_email.text = spannableString


            TextView_contact_email.setOnClickListener(object :View.OnClickListener
            {
                override fun onClick(v: View?) {
                   SendMailCallBack.sendMail(model,adapterPosition,v!!,topic)
                }

            })



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_email_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return email.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=email[position]
        holder.bind(model,holder.adapterPosition)
    }

}
