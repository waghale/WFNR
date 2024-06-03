package com.org.wfnr_2024.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.org.wfnr_2024.Activity.Special_interest_group_activity
import com.org.wfnr_2024.R


class SIGFragment:Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sig, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val TextView_visit_sig: TextView =view.findViewById(R.id.TextView_visit_sig)

        TextView_visit_sig.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.TextView_visit_sig->
            {
                val intent: Intent = Intent(activity, Special_interest_group_activity::class.java)
                startActivity(intent)

            }
        }
    }
}