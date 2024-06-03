package com.org.wfnr_2024.Room_Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class WFNR_ViewModelFactory(val repository: WFNR_Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WFNR_ViewModel::class.java)) {
                return WFNR_ViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
