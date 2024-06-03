package com.org.wfnr_2024.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WFNRProviderFactory(val WFNRRespository: WFNRRespository, val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WFNR_ViewModel(WFNRRespository,application) as T
    }
}