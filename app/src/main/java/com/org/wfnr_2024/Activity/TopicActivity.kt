package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.wfnr_2024.Adapter.TopicAdapter
import com.org.wfnr_2024.Interface.Topic_CallBack
import com.org.wfnr_2024.Model.FEEDBACK.FeedBack_Request
import com.org.wfnr_2024.Model.GET_SESSIONS.GET_SESSION_RESPONSE
import com.org.wfnr_2024.Model.Topic_Question_Server.Topic_Question_Request
import com.org.wfnr_2024.R

import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.ConstantsApp.Companion.separateDateAndTime
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.ViewModel.ResourceApp
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_FeedBack
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Itinerary
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Notes
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.TOPIC_MODEL.Topic_Question
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TopicActivity : AppCompatActivity(), Topic_CallBack, View.OnClickListener {




    lateinit var recyclerView: RecyclerView
    lateinit var topicadapter :TopicAdapter
lateinit var noTopicTextView : TextView
lateinit var Topic_btn:ImageView
lateinit var TextView_noTopic:TextView

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    var member_id:String?=null
    var session_id:String?=null
    var room_name:String?=null
    var question:String?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        Topic_btn=findViewById(R.id.Topic_btn)
        TextView_noTopic=findViewById<TextView>(R.id.TextView_noTopic)
        recyclerView = findViewById(R.id.RecyclerView_topic)

        TextView_noTopic.visibility=View.GONE

        Topic_btn.setOnClickListener(this)


        getViewModel()
        createRoomDatabase()

        //getMemberID()

        member_id=sessionManager.getMember_ID()


        //showDialog()

        // In your TopicActivity's onCreate() method or wherever you need to retrieve the data
       // val sessionData = intent.getSerializableExtra("sessionData") as? Get_Session

        val sessionData=sessionManager.getSession()
        if (sessionData != null) {

             session_id=sessionData.id
            room_name=sessionData.room!!.name




            val programpoints=sessionData.program_points
            Log.d(ConstantsApp.TAG,"programpoints=>"+programpoints)
            // Retrieve session ID from intent extras
            // Retrieve data sent via Intent
            val sessionID =sessionData.id
            val sessionName = sessionData.timeslotTitle
            val title = sessionData.title



            val date =  ConstantsApp.getFormattedDate(sessionData.date!!)
            val roomName = sessionData.room.name
            val time = ConstantsApp.formatTime(sessionData.begin!!) + " - " + ConstantsApp.formatTime(
                sessionData.end!!
            )
            // Example usage:
            Log.d("mytag", "Session ID: $sessionID")
            Log.d("mytag", "sessionName : $sessionName")
            Log.d("mytag", "title: $title")

            Log.d("mytag", "Date: $date")
            Log.d("mytag", "Room Name: $roomName")
            Log.d("mytag", "Time: $time")

            Log.d(ConstantsApp.TAG,"programPoints=>"+sessionData.program_points)

            Log.d(ConstantsApp.TAG,"programPoints size=>"+sessionData.program_points!!.size)

            val size=sessionData.program_points.size

            when(size)
            {
                0->
                {
                    TextView_noTopic.visibility=View.VISIBLE
                    recyclerView.visibility=View.GONE
                }
                else->
                {
                    TextView_noTopic.visibility=View.GONE
                    recyclerView.visibility=View.VISIBLE
                    updateRecyclerView(sessionData.program_points!!)

                }
            }




            // Find the TextView by ID
            val topicTextView = findViewById<TextView>(R.id.Topic_text)
            val topicTitleTextView = findViewById<TextView>(R.id.Topic_title1)
            //noTopicTextView = findViewById<TextView>(R.id.no_topic_text_view)
            val Dashboard_back = findViewById<ImageView>(R.id.Topic_btn)

            // Display the retrieved data in the TextView
            val topicText = "$date\n$roomName ($time)"
            topicTextView.text = topicText

            val topicTitle = "$sessionName: $title"
            topicTitleTextView.text = topicTitle
        } else {
            // Handle case where data is not available or invalid
        }






    }

    private fun getMemberID() {
        viewModel1.getAll_Member.observe(this, Observer {
            response->

            var member_data=response

            val email=sessionManager.getUserEmail()

            for (member in member_data)
            {
                if (email.equals(member.user_email_id))
                {
                    member_id=member.member_id
                    Log.d(ConstantsApp.TAG,"member_id=>"+member_id)
                }

            }

        })
    }

    private fun getViewModel() {
        val WFNRRespository= WFNRRespository()
        val WFNRProviderFactory= WFNRProviderFactory(WFNRRespository,application)
        viewModel= ViewModelProvider(this,WFNRProviderFactory).get(WFNR_ViewModel::class.java)

        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage(getString(R.string.please_wait))
        }

        sessionManager= SessionManager(this)



    }

    private fun createRoomDatabase() {
        val database = WFNR_Room_Database.getDatabase(this)

        val GET_DAY_ROOM_DAO : GET_DAY_ROOM_DAO = database.GET_DAY_ROOM_DAO()
        val GET_SESSION_DAO: GET_SESSION_DAO=database.GET_SESSION_DAO()
        val GET_FILTER_DATA_LOCAL_DAO: GET_FILTER_DATA_LOCAL_DAO =database.GET_FILTER_DATA_LOCAL_DAO()
        val TOPIC_NOTE_DAO= database.TOPIC_NOTE_DAO()
        val TOPIC_QUESTION_DAO= database.TOPIC_QUESTION_DAO()
        val TOPIC_FEEDBACK_DAO= database. TOPIC_FEEDBACK_DAO()
        val TOPIC_ITINERARY_DAO= database.TOPIC_ITINERARY_DAO()
        val LOGIN_LOCAL_DAO= database.LOGIN_LOCAL_DAO()
        val SESSION_DAO= database.SESSION_DAO()

        val repository = WFNR_LOCAL_Repository(GET_DAY_ROOM_DAO,GET_SESSION_DAO,GET_FILTER_DATA_LOCAL_DAO,TOPIC_NOTE_DAO,TOPIC_QUESTION_DAO,TOPIC_FEEDBACK_DAO,TOPIC_ITINERARY_DAO,LOGIN_LOCAL_DAO,SESSION_DAO, database)

        viewModel1 = ViewModelProvider(this, WFNR_LOCAL_ViewModelFactory(repository)).get(WFNR_LOCAL_ViewModel::class.java)
    }

    private fun showNoInternetAlert(message:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.intenet_alert)

        // Find views in the custom dialog layout
        val notificationTitle: TextView = dialog.findViewById(R.id.notificationTitle)
        val textViewLogoutConfirmation: TextView = dialog.findViewById(R.id.textViewLogoutConfirmation)
        val buttonYes: Button = dialog.findViewById(R.id.buttonYes)
        val buttonNo: Button = dialog.findViewById(R.id.buttonNo)

        // Set notification title and confirmation text
        notificationTitle.text = "Connectivity Issue"
        textViewLogoutConfirmation.text = message

        // Set click listener for the Yes button
        buttonYes.setOnClickListener {
            // Handle the Yes button click event
            dialog.dismiss()
        }

        // Set click listener for the No button
        buttonNo.setOnClickListener {
            // Handle the No button click event
            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun showDialog() {

        val progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set layout parameters to center the dialog
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(progressDialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER

        progressDialog.window?.attributes = layoutParams

        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.show()

        // Example: Dismiss the dialog after a delay (simulating a task completion)
        val imageViewLoading: ImageView = progressDialog.findViewById(R.id.imageViewLoading)

        Glide.with(this)
            .asGif()
            .load(R.raw.loader)
            .into(imageViewLoading)

        imageViewLoading.postDelayed({
            progressDialog.dismiss()
        }, 2000)
    }




    private fun updateRecyclerView(members: List<Session.ProgramPoint>) {

        recyclerView.layoutManager = LinearLayoutManager(this@TopicActivity)

        if (members.isNotEmpty()) {
            // Show RecyclerView and hide "No Topics Available" TextView
            recyclerView.visibility = View.VISIBLE
           // noTopicTextView.visibility = View.GONE

            // Initialize and set the adapter

            topicadapter = TopicAdapter(members,this)
            recyclerView.adapter = topicadapter
        } else {
            // Hide RecyclerView and show "No Topics Available" TextView
            recyclerView.visibility = View.GONE
           // noTopicTextView.visibility = View.VISIBLE
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
//        // Override back button press
//        val intent = Intent(this, EventActivity::class.java)
//        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }
    private fun showError() {
        // Show "No Topics Available" TextView with error message
        noTopicTextView.text = "No Topics Available"
        recyclerView.visibility = View.GONE
        noTopicTextView.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun topic_clicked(
        data: Session.ProgramPoint,
        position: Int,
        view: View,
        text:String
    ) {

        when(text)
        {
            "favourite"->
            {

                val isLogin=sessionManager.getLogin()
                when(isLogin)
                {
                    "1"->
                    {
                        InsertFavourite(view,data)
                    }
                    else->
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }
            "notes"->
            {
                val isLogin=sessionManager.getLogin()
                when(isLogin)
                {
                    "1"->
                    {
                        showNotesDialog(data)
                    }
                    else->
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            "voting"->
            {


                val isLogin=sessionManager.getLogin()
                when(isLogin)
                {
                    "1"->
                    {
                        showVotingDialog(data)
                    }
                    else->
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            "question"->
            {


                val isLogin=sessionManager.getLogin()
                when(isLogin)
                {
                    "1"->
                    {
                        showQuestionDialog(data)
                    }
                    else->
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            "feedback"->
            {


                val isLogin=sessionManager.getLogin()
                when(isLogin)
                {
                    "1"->
                    {
                        showFeedBackDialog(data)
                    }
                    else->
                    {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
       /* val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun InsertFavourite(view: View, data: Session.ProgramPoint) {

        var isFavourite = false

        val favouriteIcon = view.findViewById<ImageView>(R.id.favourite)

       /* if (isFavourite) {
            favouriteIcon.setImageResource(R.drawable.favourite_filled_icon)
            try {
                Insert_Itinerary_Local(data)
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

        } else {
            favouriteIcon.setImageResource(R.drawable.favourite_icon)
            Delete_Itinerary_Local()
        }*/

        favouriteIcon.setOnClickListener {
            // Toggle the state of the boolean flag
            isFavourite = !isFavourite

            // Set the appropriate image based on the current state
            if (isFavourite) {
                favouriteIcon.setImageResource(R.drawable.favourite_filled_icon)
                Insert_Itinerary_Local(data)
            } else {
                favouriteIcon.setImageResource(R.drawable.favourite_icon)
                Delete_Itinerary_Local(data)
            }
        }
    }

    private fun Delete_Itinerary_Local(data: Session.ProgramPoint) {
       Log.d(ConstantsApp.TAG,"Delete_Itinerary_Local")

        viewModel1.Delete_Itinerary_Local(data.id!!)
        Delete_Itinerary_Local_Response()
    }

    private fun Delete_Itinerary_Local_Response() {
        viewModel1.saveSuccess.observe(this, Observer {
                response->

            when(response)
            {
                "Success"->
                {
                    Log.d(ConstantsApp.TAG,"Itinerary Inserted=>"+response)
                }
                "Failure"->
                {
                    Log.d(ConstantsApp.TAG,"Itinerary Inserted=>"+response)
                }
            }


        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Insert_Itinerary_Local(data: Session.ProgramPoint) {

        try {
            var speaker_name:String?=null

            for(data in data.speakers)
            {
                val person=data.person


                Log.d(ConstantsApp.TAG,"title=>"+person!!.title)

                if (person.title.isNullOrBlank())
                {
                    speaker_name=person.firstName+" "+person.lastName
                }
                else{
                    speaker_name=person.title+" "+person.firstName+" "+person.lastName
                }
            }


            val date=data.created_at
            val begin=ConstantsApp.formatTime(data.begin!!)
            val end=ConstantsApp.formatTime(data.end!!)
            val topic_name=data.primaryTitle
            val speaker_name1=speaker_name
            val topic_itinerary=""
            val wcnr_id=sessionManager.getMember_ID()//member_id
            //val wcnr_id="1"//member_id
            val wcnr_name=""
            val wcnr_email=""
            val inserted_date=ConstantsApp.getCurrentDate()
            val topic_id=data.id
            val is_fav="1"
            val begin_formated=ConstantsApp.convertGMT7ToLocalTime(data.begin)
            val end_formated=ConstantsApp.convertGMT7ToLocalTime(data.end)

            val (date1, time) = separateDateAndTime(begin_formated)
            val (date2, time2) = separateDateAndTime(end_formated)


            val begin_date_formated=date1

            //val begin_date_formated="2024-05-24"
            //val begin_time_formated=time

            val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"))
            val adjustedTime = localTime.minusMinutes(10)
            val begin_time_formated = adjustedTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))

            //val begin_time_formated="14:20:00"
            val end_date_formated=date2

            val end_time_formated=time2





            val Topic_Itinerary= Topic_Itinerary( 0,
                date!!,
                begin ,
                end ,
                topic_name!! ,
                speaker_name!! ,
                topic_itinerary ,
                wcnr_id!! ,
                wcnr_name ,
                wcnr_email ,
                inserted_date ,
                topic_id!! ,
                is_fav, room_name!!,begin_formated,end_formated,begin_date_formated, begin_time_formated, end_date_formated, end_time_formated
            )

            viewModel1.Insert_Itinerary_Local(Topic_Itinerary)
            Itinerary_Local_Response()

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }




    }

    private fun Itinerary_Local_Response() {
        viewModel1.saveSuccess.observe(this, Observer {
            response->

            when(response)
            {
                "Success"->
                {
                    Log.d(ConstantsApp.TAG,"Itinerary Inserted=>"+response)
                }
                "Failure"->
                {
                    Log.d(ConstantsApp.TAG,"Itinerary Inserted=>"+response)
                }
            }


        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    private fun showNotesDialog(data: Session.ProgramPoint) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_popup_notes_layout, null)
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)
        val TextView_topic:TextView=dialogView.findViewById(R.id.TextView_topic)
        val TextView_speaker:TextView=dialogView.findViewById(R.id.TextView_speaker)
        val TextView_Add:TextView=dialogView.findViewById(R.id.TextView_Add)
        val TextView_Cancel:TextView=dialogView.findViewById(R.id.TextView_Cancel)
        val EditText_topic_note:EditText=dialogView.findViewById(R.id.EditText_topic_note)





        TextView_topic.text=data.primaryTitle

        for(data in data.speakers)
        {
            val person=data.person


            Log.d(ConstantsApp.TAG,"title=>"+person!!.title)

            if (person.title.isNullOrBlank())
            {
                TextView_speaker.text=person!!.firstName+" "+person.lastName
            }
            else{
                TextView_speaker.text=person!!.title+" "+person.firstName+" "+person.lastName
            }
        }
       // TextView_speaker.text=data.primaryTitle


        TextView_Cancel.setOnClickListener {
            dialog.dismiss()
        }

        TextView_Add.setOnClickListener {
            val notes=EditText_topic_note.text.toString()
            InsertTopicNotes(data,notes)
            dialog.dismiss()
        }





        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun InsertTopicNotes(data: Session.ProgramPoint, notes: String) {

        val speakers = data.speakers
        var person_name=""

        for (speaker in speakers)
        {
            val person = speaker.person

            if (person!!.title.isNullOrBlank())
            {
                 person_name=person.firstName+" "+person.firstName
            }
            else
            {
                 person_name=person.title+" "+person.firstName+" "+person.firstName
            }
        }




        val topicNoteRequest=Topic_Notes(0,data.created_at!!,data.begin!!,data.end!!,data.primaryTitle!!,person_name,notes,
            member_id!!,data.id!!,sessionManager.getUserEmail()!!,ConstantsApp.getCurrentDate())

        viewModel1.insertTopicNote(topicNoteRequest)
        InsertNotesResponseLocal()

    }

    private fun InsertNotesResponseLocal() {
        viewModel1.saveSuccess.observe(this, Observer {
            response->
            when(response)
            {
                "Success"->
                {
                    Log.d(ConstantsApp.TAG,"InsertNotesResponseLocal=>"+response)

                }
                "Failure"->
                {
                    Log.d(ConstantsApp.TAG,"InsertNotesResponseLocal=>"+response)
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    private fun showQuestionDialog(data: Session.ProgramPoint) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_popup_question_layout, null)
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)
        val TextView_topic:TextView=dialogView.findViewById(R.id.TextView_topic)
        val TextView_speaker:TextView=dialogView.findViewById(R.id.TextView_speaker)
        val TextView_Question_submit:TextView=dialogView.findViewById(R.id.TextView_Question_submit)
        val TextView_Question_Cancel:TextView=dialogView.findViewById(R.id.TextView_Question_Cancel)
        var EditText_question:EditText=dialogView.findViewById(R.id.EditText_question)


        question=EditText_question.text.toString()







        TextView_topic.text=data.primaryTitle

        for(data in data.speakers)
        {
            val person=data.person


            Log.d(ConstantsApp.TAG,"title=>"+person!!.title)

            if (person.title.isNullOrBlank())
            {
                TextView_speaker.text=person.firstName+" "+person.lastName
            }
            else{
                TextView_speaker.text=person.title+" "+person.firstName+" "+person.lastName
            }
        }
        // TextView_speaker.text=data.primaryTitle

        TextView_Question_submit.setOnClickListener {

            val question = EditText_question.text.toString()
            Log.d(ConstantsApp.TAG,"Question=>"+question)





            if (question != null) {
                // If rating1 is not null, proceed with storing feedback
                dialog.dismiss()
                StoreQuestionLocal(data, question!!)
            } else {

                dialog.dismiss()
                // If rating1 is null, show an error message or handle the case accordingly
                //Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show()
                val Message="You have to enter the question before submitting"

                showAlertDialog(Message)
            }

        }
        TextView_Question_Cancel.setOnClickListener {

            dialog.dismiss()

        }






        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun StoreQuestionLocal(data: Session.ProgramPoint, question: String) {

        Log.d(ConstantsApp.TAG,"Question=>"+question)
        try {
            var speaker_name:String?=null

            for(data in data.speakers)
            {
                val person=data.person


                Log.d(ConstantsApp.TAG,"title=>"+person!!.title)

                if (person!!.title.isNullOrBlank())
                {
                    speaker_name=person.firstName+" "+person.lastName
                }
                else{
                    speaker_name=person!!.title+" "+person.firstName+" "+person.lastName
                }
            }

            val date=data.created_at
            val begin=data.begin
            val end=data.end
            val topic_question=question
            val topic_note=data.id//topic_id
            val wcnr_id=member_id
           // val wcnr_id="1"
            val wcnr_name=""
            val wcnr_email=sessionManager.getUserEmail()
            //val wcnr_email=""
            val inserted_date=ConstantsApp.getCurrentDate()
            val session_id1=session_id
            val hall=room_name
            val topic_name=data.primaryTitle

            val Topic_Question=Topic_Question(0,date!!,begin!!,end!!,topic_question,
                speaker_name!!,
                topic_note!!,
                wcnr_id!!,
                wcnr_name,wcnr_email!!,
                inserted_date,session_id1!!,
                hall!!,topic_name!!)



            viewModel1.storeQuestionLocal(Topic_Question)
            storeQuestionLocalResponse()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }

    private fun storeQuestionLocalResponse() {
       viewModel1.saveQuestionSuccess.observe(this, Observer {
           response->

           when(response.success)
           {
               true->
               {
                   if (ConstantsApp.checkInternetConenction(applicationContext))
                   {
                       val member_id=response.wcnrId
                       val session_id=response.topic_note
                       val topic_id=response.session_id
                       val question=response.topic_question
                       val hall=response.room_name


                       val Topic_Question_Request= Topic_Question_Request(member_id,session_id,topic_id,question,hall)
                       StoreQuestionToServer(Topic_Question_Request)
                   }
                   else
                   {
                       showAlertDialog(getString(R.string.no_internet_connection))
                   }
               }
               false->
               {
                   showAlertDialog(getString(R.string.something_wrong))
               }
           }
       })
    }

    private fun StoreQuestionToServer(topicQuestionRequest: Topic_Question_Request) {

        viewModel.storeQuestion(topicQuestionRequest,progressDialog)
        TopicQuestionServerResponse()


    }

    private fun TopicQuestionServerResponse() {
       viewModel.storeQuestionLiveData.observe(this, Observer {
           response->

           when(response)
           {
               is ResourceApp.Success->
               {
                   when(response.data!!.status_code)
                   {
                       200->
                       {
                           Log.d(ConstantsApp.TAG,"Question Server Response=>"+response.data.message)

                           showMessageDialog(response.data.message)
                       }
                   }
                   progressDialog.dismiss()
               }
               is ResourceApp.Error->
               {
                   progressDialog.dismiss()
               }
               is ResourceApp.Loading->
               {
                 progressDialog.show()
               }

               else -> {}
           }

       })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    private fun showFeedBackDialog(data: Session.ProgramPoint) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_popup_feedback_layout, null)
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)
        val TextView_topic:TextView=dialogView.findViewById(R.id.TextView_topic)
        val TextView_cancel:TextView=dialogView.findViewById(R.id.TextView_cancel)
        val TextView_FeedBack_submit:TextView=dialogView.findViewById(R.id.TextView_FeedBack_submit)
        val EditText_comments:EditText=dialogView.findViewById(R.id.EditText_comments)




        val ratingBar: AppCompatRatingBar =dialogView.findViewById(R.id.ratingBar)

        var rating1:String?=null


        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // Handle the rating value
            rating1=rating.toString()
        }



        TextView_FeedBack_submit.setOnClickListener {


            if (rating1 != null) {

                var comments=EditText_comments.text.toString()
                // If rating1 is not null, proceed with storing feedback
                StoreFeedBack(data, rating1!!, comments)
                dialog.dismiss()

            } else {
                // If rating1 is null, show an error message or handle the case accordingly
               // Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show()
                val Message="You have to select the rating before submitting"
                dialog.dismiss()

                showAlertDialog(Message)
            }
        }

        TextView_cancel.setOnClickListener {
            dialog.dismiss()
        }




        TextView_topic.text=data.primaryTitle








        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun StoreFeedBack(
        data: Session.ProgramPoint,
        rating1: String?,
        comments: String
    ) {

        var speaker_name:String?=null

        for(data in data.speakers)
        {
            val person=data.person


            Log.d(ConstantsApp.TAG,"title=>"+person!!.title)

            if (person.title.isNullOrBlank())
            {
                speaker_name=person.firstName+" "+person.lastName
            }
            else{
                speaker_name=person.title+" "+person.firstName+" "+person.lastName
            }
        }



        val date=data.created_at
        val begin= ConstantsApp.formatTime(data.begin!!)
        val end=ConstantsApp.formatTime(data.end!!)
        val topic_name=data.primaryTitle

        val topic_rating=rating1
        val wcnr_id=member_id
        val wcnr_name=comments
        val wcnr_email=sessionManager.getUserEmail()
        val inserted_date=ConstantsApp.getCurrentDate()
        val topic_id=data.id

        val Topic_FeedBack= Topic_FeedBack(0,
            date!!,begin,end,
            topic_name!!,
            speaker_name!!, topic_rating!!, wcnr_id!!,wcnr_name, wcnr_email!!,inserted_date,
            topic_id!!
        )
        viewModel1.storeFeedBack(Topic_FeedBack)
        storeFeedBackResponse()
    }

    private fun storeFeedBackResponse() {
        viewModel1.saveFeedBackSuccess.observe(this, Observer {
            response->
            Log.d(ConstantsApp.TAG,"response=>"+response)
            when(response.success)
            {
                true->
                {
                    if (ConstantsApp.checkInternetConenction(applicationContext))
                    {
                        val member_id=response.wcnrId
                        val topic_id=response.topicId
                        val rating=response.topicRating
                        val comments=response.wcnrName
                        val FeedBack_Request= FeedBack_Request(member_id, topic_id, rating, comments)
                        StoreFeedBackToServer(FeedBack_Request)
                    }
                    else
                    {
                        showAlertDialog("No Internet Connection")
                    }

                }
                false->
                {
                    showAlertDialog("Something Wrong")
                }

                else -> {}
            }
        })
    }

    private fun StoreFeedBackToServer(FeedBack_Request: FeedBack_Request) {

        viewModel.store_feedback_to_server(progressDialog,FeedBack_Request)
        storeFeedBackServerResponse()
    }

    private fun storeFeedBackServerResponse() {
        viewModel.storeFeedBackLiveData.observe(this, Observer {
            response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    val data=response.data
                    Log.d(ConstantsApp.TAG,"Feedback Message"+response.msg)
                    progressDialog.dismiss()
                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()
                }
                is ResourceApp.Loading->
                {
                    progressDialog.show()
                }

                else -> {}
            }

        })
    }


    @SuppressLint("MissingInflatedId")
    private fun showVotingDialog(data: Session.ProgramPoint) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_popup_voting_layout, null)

        val TextView_Okay=dialogView.findViewById<TextView>(R.id.TextView_Okay)
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)

        TextView_Okay.setOnClickListener {
            dialog.dismiss()
        }









        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }


    @SuppressLint("MissingInflatedId")
    private fun showAlertDialog(Message: String) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_alert_layout, null)

        val TextView_message:TextView=dialogView.findViewById(R.id.TextView_message)
        val TextView_okay:TextView=dialogView.findViewById(R.id.TextView_okay)

        TextView_message.text=Message



        TextView_okay.setOnClickListener {
            dialog.dismiss()
        }
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)











        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showMessageDialog(Message: String) {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_message_layout, null)

        val TextView_message:TextView=dialogView.findViewById(R.id.TextView_message)
        val TextView_okay:TextView=dialogView.findViewById(R.id.TextView_okay)

        TextView_message.text=Message



        TextView_okay.setOnClickListener {
            dialog.dismiss()
        }
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)











        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.Topic_btn->
            {
                gotoScreen(this,EventActivity::class.java)
            }
        }
    }

    fun gotoScreen(context: Context?, cls: Class<*>?) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }
}
