package com.org.wfnr_2024.Fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment

import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.SessionManager


class AppHelpFragment:AppCompatActivity() {


    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_app_help)


        val videoView:VideoView = findViewById<VideoView>(R.id.videoView)



        sessionManager=SessionManager(this)



        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(
            Uri.parse(
                "android.resource://" + this.packageName + "/" +
                        R.raw.wfnr_app_help
            )
        )

        videoView.start()
    }






    /*private fun setActivity(fragment:String?) {
        val intent = Intent(requireContext(), FragmentwithBackActivity::class.java)
        val bundle = Bundle()
        bundle.putString("fragment",fragment)
        intent.putExtras(bundle)
        startActivity(intent)
    }*/
}