package com.org.wfnr_2024.Activity


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.org.wfnr_2024.R

class UsefulInfoActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var Event_backbtn: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_useful_info)

        val textViewInfo: TextView = findViewById(R.id.textViewInfo)

        Event_backbtn=findViewById(R.id.Event_backbtn)

        Event_backbtn.setOnClickListener(this)

        val checkInInfo = "Check-in opening hours\n\n22 May, 8:00 a.m. – 4:30 p.m.\n23 May, 8:00 a.m. – 6:15 p.m.\n24 May, 8:00 a.m. – 5:30 p.m.\n25 May, 8:30 a.m. – 2:00 p.m.\n\n"
        val certificateInfo = "Certificate of attendance\n\nIn order to receive a certificate, you need to scan your badge every day at the certification station near the check-in counter.\nThe certificate will be sent to you via email after the congress.\n\n"
        val cateringInfo = "Catering\n\nRefreshments are available at the bistro in the exhibition hall.\n\n"
        val socialProgrammeInfo = "Social Programme\n\nYou can sign up for these events in the online registration form.\nTour of the GF Strong Rehabilitation Centre\nDate:\t\t\tWednesday 22 May 2024\nTime:\t\t\t8:30 a.m. - 10:00 a.m.\nFee:\t\t\t10 Euro\nMax. # of participants:\t25\nTour of ICORD – International Collaboration on Repair Discoveries (ICORD)\nDate:\t\t\tWednesday 22 May 2024\nTime:\t\t\t5:30 PM - 7:30 PM\nFee:\t\t\t10 Euro\nMax. # of participants:\t100\nWelcome reception\nDate:\t\t\tThursday 23 May 2024\nTime:\t\t\t7:45 PM - 9:00 PM\nFee:\t\t\t0 Euro\n\n"
        val industryExhibitionInfo = "Industry Exhibition\n\nOpen in the breaks between sessions\n23 May, 8:00 a.m. – 8:00 p.m.\n24 May, 8:00 a.m. – 6:30 p.m.\n25 May, 8:30 a.m. – 3:00 p.m."

        val spannableBuilder = SpannableStringBuilder()
        spannableBuilder.append(checkInInfo)
        spannableBuilder.append(certificateInfo)
        spannableBuilder.append(cateringInfo)
        spannableBuilder.append(socialProgrammeInfo)
        spannableBuilder.append(industryExhibitionInfo)

        // Applying styles
        applyStyle(spannableBuilder, "Check-in opening hours\n", Color.parseColor("#0A5050"), Typeface.BOLD)
        applyStyle(spannableBuilder, "Certificate of attendance\n", Color.parseColor("#0A5050"), Typeface.BOLD)
        applyStyle(spannableBuilder, "Catering\n", Color.parseColor("#0A5050"), Typeface.BOLD)
        applyStyle(spannableBuilder, "Social Programme\n", Color.parseColor("#0A5050"), Typeface.BOLD)
        applyStyle(spannableBuilder, "Industry Exhibition\n", Color.parseColor("#0A5050"), Typeface.BOLD)

        // Applying different color for other text
        applyStyle(spannableBuilder, checkInInfo.substringAfter("\n"), Color.parseColor("#19A887"), Typeface.NORMAL)
        applyStyle(spannableBuilder, certificateInfo.substringAfter("\n"), Color.parseColor("#19A887"), Typeface.NORMAL)
        applyStyle(spannableBuilder, cateringInfo.substringAfter("\n"), Color.parseColor("#19A887"), Typeface.NORMAL)
        applyStyle(spannableBuilder, socialProgrammeInfo.substringAfter("\n"), Color.parseColor("#19A887"), Typeface.NORMAL)
        applyStyle(spannableBuilder, industryExhibitionInfo.substringAfter("\n"), Color.parseColor("#19A887"), Typeface.NORMAL)

        textViewInfo.text = spannableBuilder
    }

    private fun applyStyle(spannable: SpannableStringBuilder, text: String, color: Int, style: Int) {
        val start = spannable.indexOf(text)
        if (start != -1) {
            spannable.setSpan(ForegroundColorSpan(color), start, start + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(style), start, start + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this,AboutUsActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Event_backbtn->
            {
                val intent=Intent(this,AboutUsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
