package com.org.wfnr_2024.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.org.wfnr_2024.Adapter.ImageAdapter
import com.org.wfnr_2024.R
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Math.abs
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private lateinit var handler_viewpager2: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var imageAdapter: ImageAdapter
    private val handler = Handler(Looper.getMainLooper())

    lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Setup ViewPager and transformers
        viewPager2 = view.findViewById(R.id.ViewPager2)
        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
    }

    private fun init() {
        handler_viewpager2 = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.slider2)
        imageList.add(R.drawable.slider3)
        imageList.add(R.drawable.action_banner)

        imageAdapter = ImageAdapter(imageList, viewPager2)
        viewPager2.adapter = imageAdapter

        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        handler_viewpager2.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler_viewpager2.postDelayed(runnable, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler_viewpager2.removeCallbacks(runnable)
    }

}
