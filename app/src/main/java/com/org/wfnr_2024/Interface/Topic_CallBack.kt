package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session

interface Topic_CallBack {

    fun topic_clicked(data: Session.ProgramPoint,position:Int,view: View,text:String)
}