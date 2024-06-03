package com.org.wfnr_2024.WFNR_ROOM_DATABASE

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WFNR_LOCAL_ViewModelFactory(val repository: WFNR_LOCAL_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WFNR_LOCAL_ViewModel::class.java)) {
            return WFNR_LOCAL_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
