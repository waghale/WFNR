package com.org.wfnr_2024.Interface

import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_feedback

interface FeedBack_Callback {

    fun FeedBack_rating(data: Section_feedback,position:Int)


}