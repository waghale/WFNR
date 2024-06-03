package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session

interface Get_Session_CallBack {

    fun getSessionCall(data:Session,position:Int,view:View)
}