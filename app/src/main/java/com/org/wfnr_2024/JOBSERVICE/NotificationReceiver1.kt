package com.org.wfnr_2024.JOBSERVICE

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

import com.org.wfnr_2024.Activity.MainActivity
import com.org.wfnr_2024.R

import java.util.*

class NotificationReceiver1 : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "daily_notification_channel"
        private const val NOTIFICATION_ID = 1
        private const val MAX_NOTIFICATION_COUNT = 2
        private const val NOTIFICATION_HOUR = 7
        private const val NOTIFICATION_MINUTE = 0
    }

    private var notificationCount = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "formattedTimein AlarmReceiver")

        if (notificationCount >= MAX_NOTIFICATION_COUNT) {
            notificationCount = 0
            setNextNotification(context)
        } else {
            context?.let { createNotificationChannel(it) }
            context?.let { sendNotification(it) }
            notificationCount++
        }
    }

    private fun setNextNotification(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(context, NotificationReceiver1::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, NOTIFICATION_HOUR)
            set(Calendar.MINUTE, NOTIFICATION_MINUTE)
            add(Calendar.DAY_OF_MONTH, 1) // Set the next day for the alarm
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    private fun sendNotification(context: Context) {
        // Create notification content
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("fragment", "notification")
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val largeIcon = BitmapFactory.decodeResource(context.resources,
            R.drawable.icon
        )

        val notification = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(largeIcon)
            .setContentTitle("IAN Quiz of The Day")
            .setContentText("Attempt Today's question for your daily dose of Learning with Fun!!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, notification)

        Log.d("mytag", "NOTIFICATION_ID=>$NOTIFICATION_ID")
    }

    private fun createNotificationChannel(context: Context) {
        val name = "IAN Quiz of The Day"
        val descriptionText = "Attempt Today's question for your daily dose of Learning with Fun"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}


