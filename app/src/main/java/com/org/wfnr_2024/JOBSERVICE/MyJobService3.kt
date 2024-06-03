package com.org.wfnr_2024.JOBSERVICE

import android.app.*
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
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
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MyJobService3 : JobService() {

    lateinit var viewModel1: WFNR_LOCAL_ViewModel
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
                val latestBeginDateTime = getEarliestFutureDateTime(earliestItinerary)

                if (latestBeginDateTime != null) {
                    Log.d(ConstantsApp.TAG, "getEarliestFutureDateTime=>"+latestBeginDateTime)
                    Log.d(ConstantsApp.TAG, "time=>"+latestBeginDateTime.time)
                    setupNotificationAlarm(latestBeginDateTime)
                } else {
                    Log.e(ConstantsApp.TAG, "latestBeginDateTime is null")
                }
               /* Log.d(ConstantsApp.TAG,"getEarliestFutureDateTime=>"+latestBeginDateTime)
                Log.d(ConstantsApp.TAG,"time=>"+latestBeginDateTime!!.time)
                setupNotificationAlarm(latestBeginDateTime)*/
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

    companion object {
        private const val JOB_ID = 123
        //private const val JOB_PERIODIC_INTERVAL_MS = 15 * 60 * 1000 // 15 minutes in milliseconds
        private const val JOB_PERIODIC_INTERVAL_MS = 5 * 60 * 1000 // 5 minutes in milliseconds

        fun scheduleJob(context: Context) {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder(
                JOB_ID,
                ComponentName(context, MyJobService3::class.java)
            )
                .setPeriodic(JOB_PERIODIC_INTERVAL_MS.toLong())
                .build()
            jobScheduler.schedule(jobInfo)
        }

        fun cancelJob(context: Context) {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
        }
    }
}
