package com.org.wfnr_2024.Interface

import com.org.wfnr_2024.Model.Notification.notification
import retrofit2.http.GET

interface NotificationApiService {

    interface ApiService {
        @GET("getChangeByNotification")
         fun getNotifications(): notification
    }


}
