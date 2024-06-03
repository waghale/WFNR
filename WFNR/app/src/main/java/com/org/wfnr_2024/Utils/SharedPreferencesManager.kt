package com.org.wfnr_2024.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManagerToken(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "token_key"
        private const val ACTIVITY_KEY = "ACTIVITY_KEY"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }


}
