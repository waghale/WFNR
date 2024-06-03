package com.org.wfnr_2024.Interface

import android.view.View
import com.org.wfnr_2024.Model.WCNR_Section_menu.Section_Menu

interface MenuCallBack {

    fun menuClicked(data: Section_Menu,position:Int,view: View)
}