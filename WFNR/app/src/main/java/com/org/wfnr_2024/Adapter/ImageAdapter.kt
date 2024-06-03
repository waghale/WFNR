package com.org.wfnr_2024.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.org.wfnr_2024.R

class ImageAdapter(private val imageList:ArrayList<Int>, private val viewPager2: ViewPager2):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){


    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val imageView:ImageView=itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.adapter_image,parent,false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])


        /* if (position==imageList[position]-1)
         {
             viewPager2.post(runnable)
         }*/

        /* if (position == imageList.size - 2) {
             viewPager2.post(runnable)
         }*/

        if (position==imageList.size-1)
        {
            viewPager2.post(runnable)
        }
    }

    private val runnable= Runnable {

        imageList.addAll(imageList)
        notifyDataSetChanged()

    }


}