package com.org.wfnr_2024.JOBSERVICE

import android.app.*
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.ConstantsApp.Companion.getEarliestFutureDateTime
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.ItineraryDetails


import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyJobService2 : JobService() {


    private lateinit var repository: WFNR_LOCAL_Repository

    override fun onCreate() {
        super.onCreate()
        val database = WFNR_Room_Database.getDatabase(applicationContext)

        // Initialize the repository
        repository = WFNR_LOCAL_Repository(
            GET_DAY_ROOM_DAO = database.GET_DAY_ROOM_DAO(),
            GET_SESSION_DAO = database.GET_SESSION_DAO(),
            GET_FILTER_DATA_LOCAL_DAO = database.GET_FILTER_DATA_LOCAL_DAO(),
            TOPIC_NOTE_DAO = database.TOPIC_NOTE_DAO(),
            TOPIC_QUESTION_DAO = database.TOPIC_QUESTION_DAO(),
            TOPIC_FEEDBACK_DAO = database.TOPIC_FEEDBACK_DAO(),
            TOPIC_ITINERARY_DAO = database.TOPIC_ITINERARY_DAO(),
            LOGIN_LOCAL_DAO = database.LOGIN_LOCAL_DAO(),
            SESSION_DAO=database.SESSION_DAO(),
            database = database
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartJob(params: JobParameters): Boolean {


         CoroutineScope(Dispatchers.IO).launch {

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val sqlDateTime = LocalDateTime.now().format(formatter)


            val earliestItinerary = repository.getRecordsBeforeDateTime(sqlDateTime)
            if (earliestItinerary != null) {

                Log.d(ConstantsApp.TAG,"earliestItinerary=>"+earliestItinerary)

                for(data in earliestItinerary)
                {
                    Log.d(ConstantsApp.TAG,"Date=>"+data.begin_date_formated+" Time=>"+data.begin_time_formated)
                }

                val latestBeginDateTime = getEarliestFutureDateTime(earliestItinerary)
                Log.d(ConstantsApp.TAG,"getEarliestFutureDateTime=>"+latestBeginDateTime)

                Log.d(ConstantsApp.TAG,"time=>"+latestBeginDateTime!!.time)


                setupNotificationAlarm(latestBeginDateTime)
            }
            jobFinished(params, false)
        }
        return true

    }

    override fun onStopJob(params: JobParameters): Boolean {
        return true
    }



    private fun setupNotificationAlarm(itinerary: ItineraryDetails?) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, NotificationReceiver2::class.java).apply {
            putExtra("topic_name", itinerary!!.topicName)
            putExtra("room_name", itinerary.roomName)
            putExtra("begin_time", itinerary.time)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        val (hour, minute) = itinerary!!.time.split(":").map { it.toInt() }
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

      //  calendar.add(Calendar.MINUTE, -4)

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


  /*  private fun setupNotificationAlarm(itinerary: Topic_Itinerary) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Get the current time in Canadian timezone
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Canada/Eastern"))

        // Parse the begin time from the itinerary
        val (hour, minute) = itinerary.begin.split(":").map { it.toInt() }

        // Set the calendar to the itinerary begin time
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Subtract 10 minutes from the itinerary begin time
        calendar.add(Calendar.MINUTE, -10)

        // Create an intent for the notification receiver
        val notificationIntent = Intent(this, NotificationReceiver2::class.java).apply {
            putExtra("topic_name", itinerary.topic_name)
            putExtra("begin_time", itinerary.begin)
            putExtra("room_name", itinerary.room_name)
        }

        // Create a pending intent for the notification
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm to trigger precisely at the specified time
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
    }*/


   /* private fun setupNotificationAlarm(itinerary: Topic_Itinerary) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Get the current time in GMT-7 time zone
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-7"))

        // Parse the begin time from the itinerary
        val (hour, minute) = itinerary.begin.split(":").map { it.toInt() }

        // Set the calendar to the itinerary begin time
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Subtract 10 minutes from the itinerary begin time
        calendar.add(Calendar.MINUTE, -10)

        // Create an intent for the notification receiver
        val notificationIntent = Intent(this, NotificationReceiver2::class.java).apply {
            putExtra("topic_name", itinerary.topic_name)
            putExtra("begin_time", itinerary.begin)
            putExtra("room_name", itinerary.room_name)
        }

        // Create a pending intent for the notification
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm to trigger precisely at the specified time
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
    }*/


}
