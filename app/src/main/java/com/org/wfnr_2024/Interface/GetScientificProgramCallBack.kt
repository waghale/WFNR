package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE

interface GetScientificProgramCallBack {

    fun getScientificProgram(roomName: GET_DAYS_RESPONSE.HydraMember.RoomOrder,position:Int,view: View)
}