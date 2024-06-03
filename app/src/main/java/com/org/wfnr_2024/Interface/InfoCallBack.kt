package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.Model.InfoModel.InfoData

interface InfoCallBack {

    fun Info_Menu_Clicked(position:Int,data:InfoData,view: View)
}