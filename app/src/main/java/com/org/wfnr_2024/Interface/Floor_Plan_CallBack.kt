package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.Model.Floor_Plan_Model.Floor_Plan_data

interface Floor_Plan_CallBack {

    fun Floor_Plan_clicked(data:Floor_Plan_data,position:Int,view: View)
}