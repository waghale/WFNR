package org.impactindiafoundation.wfnr.CallBack

import android.view.View
import org.impactindiafoundation.wfnr.Model.Email

interface SendMailCallBack {

    fun sendMail(data:Email,position:Int,view: View,topic:String)
}