package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.R
import org.impactindiafoundation.wfnr.Adapter.Email_Adapter
import org.impactindiafoundation.wfnr.CallBack.SendMailCallBack
import org.impactindiafoundation.wfnr.Model.Email
import org.impactindiafoundation.wfnr.Model.Special_Interest_Group_Model


class SIG_Description_Activity:AppCompatActivity(), SendMailCallBack, View.OnClickListener {

    lateinit var imageView_back:ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sig)


        val TextView_contact: TextView = findViewById(R.id.TextView_contact)
        val RecyclerView_contact:RecyclerView=findViewById(R.id.RecyclerView_contact)
        val TextView_SIG_title: TextView = findViewById(R.id.TextView_SIG_title)
        val webview: WebView = findViewById(R.id.webview)

         imageView_back=findViewById<ImageView>(R.id.imageView_back)

        imageView_back.setOnClickListener(this)




        // Retrieve the Bundle from the Intent
        val bundle = intent.extras

        // Check if the Bundle is not null
        if (bundle != null) {
            // Retrieve data from the Bundle
            val data: Special_Interest_Group_Model? = bundle.getParcelable("SPECIAL_INTEREST_GROUP_DATA")

            // Use the data as needed
            if (data != null) {
                // Do something with the data
                Log.d("mytag","data=>"+data)
                TextView_contact.text=data.description
                TextView_SIG_title.text=data.title


                val size = data.email?.size ?: 0
                if (size == 0) {
                    TextView_contact.visibility = View.GONE
                    RecyclerView_contact.visibility = View.GONE
                }
                else
                {
                    TextView_contact.visibility = View.VISIBLE
                    RecyclerView_contact.visibility = View.VISIBLE

                    val adapter1 = Email_Adapter(this, data.email!!, data.title!!,this)
                    val layoutManager1 = LinearLayoutManager(this)
                    layoutManager1.orientation = LinearLayoutManager.VERTICAL // or HORIZONTAL
                    RecyclerView_contact.layoutManager = layoutManager1

                    RecyclerView_contact.adapter = adapter1

                    if (size == 1) {
                        TextView_contact.text = "Contact"
                    } else {
                        TextView_contact.text = "Contacts"
                    }
                }

                webview.loadDataWithBaseURL(null, data.description!!, "text/html", "UTF-8", null)





            }
        }
    }

    override fun sendMail(data: Email, position: Int, view: View, topic: String) {
        // var email_CC="digitalhealth@cmplin.com"
        // var email_BCC="devcmpl@gmail.com"
        var email_TO=data.email
        var email_From=""
        var subject=topic+" "+"SIG of WFNR"

        //var message="Dear Admin, You have query from the IAN App,\n"+subject+"\n"+binding.editTextContactMessage.text.toString()+"\n"+"regards \n IAN Digital Team"

        // define Intent object with action attribute as ACTION_SEND

        // define Intent object with action attribute as ACTION_SEND
        val intent = Intent(Intent.ACTION_SEND)

        // add three fields to intent using putExtra function

        // add three fields to intent using putExtra function
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email_TO))
        // intent.putExtra(Intent.EXTRA_CC, arrayOf(email_CC))
        //intent.putExtra(Intent.EXTRA_BCC, arrayOf(email_BCC))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //intent.putExtra(Intent.EXTRA_TEXT, message)

        // set type of intent

        // set type of intent
        intent.type = "message/rfc822"

        // startActivity with intent with chooser as Email client using createChooser function

        // startActivity with intent with chooser as Email client using createChooser function
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.imageView_back->
            {
                val intent=Intent(this,Special_interest_group_activity::class.java)
                startActivity(intent)
            }
        }
    }
}