package com.org.wfnr_2024.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.org.wfnr_2024.Activity.AboutUs1Activity
import com.org.wfnr_2024.Activity.ContactUs_WFNR
import com.org.wfnr_2024.R

class MenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners for menu items
        val aboutUsTextView: RelativeLayout = view.findViewById(R.id.card)
        val cardViewAboutBNAApp = view.findViewById<TextView>(R.id.cardViewAboutBNAApp)
        val contactus = view.findViewById<TextView>(R.id.contactus)


        cardViewAboutBNAApp.setOnClickListener {
            shareAppLink()
        }
            aboutUsTextView.setOnClickListener {
            navigateToAboutUsActivity()
        }

        contactus.setOnClickListener {
            navigateTocontactUsActivity()
        }

    }

    private fun navigateTocontactUsActivity() {
        val intent = Intent(requireContext(), ContactUs_WFNR::class.java)
        startActivity(intent)    }

    private fun shareAppLink() {
        val appLink = "Download WFNR Android App : https://play.google.com/store/apps/details?id=com.org.wfnr_2024&pcampaignid=web_share"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out the WFNR Android App!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, appLink)

        startActivity(Intent.createChooser(shareIntent, "Share WFNR App"))
    }

    private fun navigateToAboutUsActivity() {
        val intent = Intent(requireContext(), AboutUs1Activity::class.java)
        startActivity(intent)
    }
}