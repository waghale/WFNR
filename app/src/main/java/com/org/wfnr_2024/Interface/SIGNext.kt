package org.impactindiafoundation.wfnr.CallBack

import android.view.View
import org.impactindiafoundation.wfnr.Model.Special_Interest_Group_Model

interface SIGNext {

    fun OnNextPage(data: Special_Interest_Group_Model,position:Int,view: View)
}