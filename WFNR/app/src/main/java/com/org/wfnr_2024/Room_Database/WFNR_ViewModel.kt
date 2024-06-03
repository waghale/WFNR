package com.org.wfnr_2024.Room_Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.org.wfnr_2024.Room_Database_Model.Days_Local
import com.org.wfnr_2024.Room_Database_Model.SessionDataClass_Local


class WFNR_ViewModel(private val repository: WFNR_Repository) : ViewModel() {


    val getAllSessionData: LiveData<List<SessionDataClass_Local>> = repository.getAllSessionData
    val getAllDaysData: LiveData<List<Days_Local>> = repository.getAllDaysData



}
