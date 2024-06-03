package com.org.wfnr_2024.Utils

import android.content.Context
import android.content.SharedPreferences
import com.org.wfnr_2024.Model.Notification.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationSharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "NotificationPreferences",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()

    // Save notifications to shared preferences
    fun saveNotificationsToPrefs(context: Context, notifications: List<Data>) {
        val sharedPreferences = context.getSharedPreferences("MyNotifications", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(notifications)
        editor.putString("notifications", json)
        editor.apply()
    }


    // Retrieve notifications from shared preferences
    fun getNotificationsFromPrefs(context: Context): List<Data> {
        val sharedPreferences = context.getSharedPreferences("MyNotifications", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notifications", null)
        val type = object : TypeToken<List<Data>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

}
