package com.org.wfnr_2024.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager1(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val SHARED_PREF_NAME = "MySharedPrefs"
    }

    fun saveSelectedDate(selectedDate: String) {
        with(sharedPreferences.edit()) {
            putString("selected_date", selectedDate)
            apply()
        }
    }

    fun getSelectedDate(): String? {
        return sharedPreferences.getString("selected_date", null)
    }

    // Add other helper methods for managing preferences as needed
}
