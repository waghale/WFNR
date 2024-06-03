package com.org.wfnr_2024.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.org.wfnr_2024.Model.ALL_Speaker.HydraMember
import com.org.wfnr_2024.Model.ALL_Speaker.Spesker_list
import com.org.wfnr_2024.Model.ALLdATA_SESSON_TOPIC.Session_with_Topic
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_filter)

        val listView: ListView = findViewById(R.id.lv1)
        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"

        // Fetch speakers and make API call for sessions
        fetchSpeakers(eventId, listView)
        makeApiCallAllSession()
    }

    private fun makeApiCallAllSession() {
        val pagination = false
        val eventId = "863f8ff3-fdd2-4d2c-b077-52f451eacfc5"
        val date = ""
        val groups = "session:item:read"

        // Make the API call using RetrofitClient to fetch sessions
        RetrofitClient.apiService.getSessions(
            pagination = pagination,
            eventId = eventId,
            date = date,
            groups = groups
        ).enqueue(object : Callback<Session_with_Topic> {
            override fun onResponse(call: Call<Session_with_Topic>, response: Response<Session_with_Topic>) {
                if (response.isSuccessful) {
                    val sessionWithTopic = response.body()
                    Log.d("mytag", "sessionWithTopic response: $sessionWithTopic")

//                    // Start Filter_Session_Topics activity and pass the sessionWithTopic object
//                    if (sessionWithTopic != null) {
//                        val intent = Intent(context, Filter_Session_Topics::class.java)
//                        intent.putExtra("sessionWithTopic", sessionWithTopic)
//                        context.startActivity(intent)
//                    } else {
//                        Log.e("mytag", "Session with topic is null")
//                    }
                } else {
                    Log.e("mytag", "API call failed with error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Session_with_Topic>, t: Throwable) {
                Log.e("mytag", "API call failed with exception: ${t.message}")
            }
        })
    }

    private fun fetchSpeakers(eventId: String, listView: ListView) {
        val apiService = RetrofitClient.apiService

        // Make the API call using RetrofitClient to fetch speakers
        apiService.getSpeakers(
            hasChairOrSpeakerEngagements = true,
            pagination = false,
            eventId = eventId
        ).enqueue(object : Callback<Spesker_list> {
            override fun onResponse(call: Call<Spesker_list>, response: Response<Spesker_list>) {
                if (response.isSuccessful) {
                    val speakerList = response.body()

                    if (speakerList != null) {
                        val members = speakerList.member

                        // Extract speaker names and display in ListView
                        val speakerNames = members.mapNotNull { member ->
                            member.firstName?.let { firstName ->
                                member.lastName?.let { lastName ->
                                    "$firstName $lastName"
                                }
                            }
                        }

                        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, speakerNames)
                        listView.adapter = adapter

                        listView.setOnItemClickListener { _, _, position, _ ->
                            val selectedMember = members[position]
                            val memberId = selectedMember.id

                            // Start Filter_Session_Topics activity and pass the member ID
                            val intent = Intent(context, Filter_Session_Topics::class.java)
                            intent.putExtra("memberId", memberId)
                            context.startActivity(intent)
                        }
                    }
                } else {
                    Log.e("mytag", "Failed to fetch speakers. Error code: ${response.code()}")
                    Log.e("mytag", "Error message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Spesker_list>, t: Throwable) {
                Log.e("mytag", "Error fetching speakers: ${t.message}")
            }
        })
    }
}
