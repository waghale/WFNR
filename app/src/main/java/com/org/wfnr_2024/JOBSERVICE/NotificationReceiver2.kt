package com.org.wfnr_2024.JOBSERVICE

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
import com.org.wfnr_2024.Activity.MainActivity
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp

import java.util.Calendar

class NotificationReceiver2 : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "WFNR_notification_channel"
        private const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent == null) return

        val topicName = intent.getStringExtra("topic_name")
        val beginTime = intent.getStringExtra("begin_time")
        val room_name = intent.getStringExtra("room_name")

        val (hour, minute) = beginTime?.split(":")?.let {
            if (it.size >= 2) {
                it[0] to it[1]
            } else {
                null
            }
        } ?: Pair("0", "0")

        Log.d(ConstantsApp.TAG,"hour=>"+hour)
        Log.d(ConstantsApp.TAG,"minute=>"+minute)



        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val hourInt = hour.toIntOrNull()
        val minuteInt = minute.toIntOrNull()

        if (currentHour == hourInt && currentMinute ==minuteInt)
        {
            context?.let { createNotificationChannel(it) }
            context?.let { sendNotification(it, topicName, beginTime, room_name) }
        }
        else
        {
            Log.d(ConstantsApp.TAG,"NotificationReceiver2=>"+currentHour+":"+currentMinute)
        }


      /*  val hourInt = hour.toIntOrNull()
        val minuteInt = minute.toIntOrNull()

        if (hourInt != null && minuteInt != null && currentHour == hourInt && currentMinute == minuteInt) {
            context.createNotificationChannel() // Create notification channel
            context.sendNotification(topicName, beginTime, roomName) // Send notification
        } else {
            Log.d(ConstantsApp.TAG, "NotificationReceiver2=>$currentHour:$currentMinute")
        }*/

    }


   /* override fun onReceive(context: Context?, intent: Intent?) {
        // Ensure context and intent are not null
        if (context == null || intent == null) return

        val topicName = intent.getStringExtra("topic_name")
        val beginTime = intent.getStringExtra("begin_time")
        val room_name = intent.getStringExtra("room_name")

        val notificationBuilder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("Upcoming Topic")
            .setContentText("Topic: $topicName begins in 10 minutes, Time is$beginTime"+"in Room"+room_name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Dismisses the notification when tapped

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, notificationBuilder.build())
        }
    }*/

    private fun sendNotification(
        context: Context,
        topicName: String?,
        beginTime: String?,
        room_name: String?
    ) {
        // Create notification content
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("fragment", "notification")
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.icon)

        // Adjust topicName if it's too long
        val adjustedTopicName = adjustTopicName(topicName)

        val notification = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(largeIcon)

            .setContentTitle("Upcoming Topic")
            .setContentText("Topic: $adjustedTopicName \n begins in 10 minutes\nTime is $beginTime\nin Room $room_name")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun adjustTopicName(topicName: String?): String {
        // Adjust topicName if it's too long
        return if (topicName != null && topicName.length > 20) {
            // Trim the topicName to a maximum of 20 characters
            topicName.substring(0, 20) + "..."
        } else {
            // Return the original topicName if it's not too long
            topicName ?: ""
        }
    }

    private fun createNotificationChannel(context: Context) {
        val name = "Section is Started after 10 minutes"
        val descriptionText = "Section is Started after 10 minutes"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}
