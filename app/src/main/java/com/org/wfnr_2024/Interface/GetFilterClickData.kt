package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.DisplayItem
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local

interface GetFilterClickData {

   // fun getFilterClickedData(data:Get_Filter_Data_Local,position:Int,view: View)
    fun getFilterClickedData(data:DisplayItem,position:Int,view: View)
}