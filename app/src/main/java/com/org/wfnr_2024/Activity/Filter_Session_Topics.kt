package com.org.wfnr_2024.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Session_with_Topic
import com.org.wfnr_2024.Model.FILTER.SessionTopicFilter
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Filter_Session_Topics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_session_topics)

//        // Retrieve memberId from intent extras
//        val memberId = intent.getStringExtra("memberId")
//        if (memberId != null) {
//            Log.d("mytag", "Received Member ID: $memberId")
//            // Perform API call to fetch person details using memberId
//            fetchPersonDetails(memberId)
//        } else {
//            Log.e("mytag", "No Member ID found in intent extras")
//        }
//    }
        val sessionWithTopic = intent.getSerializableExtra("sessionWithTopic") as? Session_with_Topic

        Log.d("mytag","sessionWithTopic=>"+sessionWithTopic)

        val personId = "bd272a1e-05b6-4d19-9553-ff24c865d700"

        // Create an instance of the Retrofit service
        val apiService = RetrofitClient.apiService

        // Make the API call to fetch person details
        apiService.getPersonDetails(personId).enqueue(object : Callback<Session_with_Topic> {
            override fun onResponse(call: Call<Session_with_Topic>, response: Response<Session_with_Topic>) {
                if (response.isSuccessful) {
                    val personDetails = response.body()
                    if (personDetails != null) {
                        // Handle the received person details here
                        Log.d("mytag", "Received Person Details: $personDetails")

                        // Access specific details (e.g., firstName, lastName, title) from personDetails
                        val firstName = personDetails.member ?: "N/A"
                        val lastName = personDetails.member ?: "N/A"
                        val title = personDetails.member ?: "N/A"

                        Log.d("mytag", "First Name: $firstName, Last Name: $lastName, Title: $title")
                    } else {
                        Log.e("mytag", "Person details are null")
                    }
                } else {
                    Log.e("mytag", "Failed to fetch person details. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Session_with_Topic>, t: Throwable) {
                Log.e("mytag", "Error fetching person details: ${t.message}")
            }
        })
    }
}
