package com.org.wfnr_2024.Activity


import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.wfnr_2024.Adapter.CustomSpinnerAdapter
import com.org.wfnr_2024.Adapter.FilterDataAdapter
import com.org.wfnr_2024.Adapter.Filter_Session_Topic_Adapter
import com.org.wfnr_2024.Adapter.RoomAdapter
import com.org.wfnr_2024.Adapter.SessionAdapter1
import com.org.wfnr_2024.Interface.GetFilterClickData
import com.org.wfnr_2024.Interface.GetScientificProgramCallBack
import com.org.wfnr_2024.Interface.Get_Session_CallBack
import com.org.wfnr_2024.Model.Chair_Speaker_List.ChairSpeakerList
import com.org.wfnr_2024.Model.GET_DAYS.GET_DAYS_RESPONSE
import com.org.wfnr_2024.R
import com.org.wfnr_2024.SQL_Database.CONVERTERS.Session.SessionDataDao
import com.org.wfnr_2024.SQL_Database.Room.RoomDao
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.Utils.SharedPreferencesManager1
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.DisplayItem
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.ItemType
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.CMPL_Local_Model.Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_DAY_ROOM.Get_Day_Room
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_FILTER_LOCAL.Get_Filter_Data_Local
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.GET_SESSION.Get_Session
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database


class EventActivity : AppCompatActivity(), GetScientificProgramCallBack, Get_Session_CallBack,
    View.OnClickListener, GetFilterClickData {

  //  private lateinit var adapter: SessionAdapter
    private lateinit var adapter: RoomAdapter
    private lateinit var adapter1: SessionAdapter1
    private lateinit var roomDao: RoomDao
    private lateinit var sessionDataDao: SessionDataDao
    private lateinit var sharedPreferencesManager: SharedPreferencesManager1
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewSession: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var imageFilter: ImageView
    private lateinit var Event_backbtn:ImageView
    lateinit var progressDialog2: ProgressDialog
    var ChairSpeakerList:ArrayList<ChairSpeakerList>?=null


    private var selectedRoomName: String? = null
    var selectedDate=""


    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


        getViewModel()
        createRoomDatabase()

        ChairSpeakerList= ArrayList()





        getDayRoomDataFromLocal()



        spinner = findViewById(R.id.spinner_dates)
        imageFilter = findViewById(R.id.imageFilter)
        Event_backbtn=findViewById(R.id.Event_backbtn)
        imageFilter.setOnClickListener(this)
        Event_backbtn.setOnClickListener(this)






    }

    private fun getDayRoomDataFromLocal() {
        viewModel1.getAll_DAy_Room.observe(this, Observer {
            response->

            Log.d(ConstantsApp.TAG,"response of getDayRoomDataFromLocal=> "+response)

            val dateList = mutableListOf<String>()

            dateList.clear()
            val getDayRoomList: List<Get_Day_Room> = response


            getDayRoomList.mapTo(dateList) { it.date!! }

            // val dateList = getDayRoomList.map { it.date }

            val customAdapter = CustomSpinnerAdapter(applicationContext, dateList)
            spinner.adapter = customAdapter


            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Get the selected date from the spinner
                     selectedDate = parent?.getItemAtPosition(position).toString()
                    
                    val selectedDayRoom = getDayRoomList.find { it.date == selectedDate }
                    selectedDayRoom?.let {
                        // Handle the selected Get_Day_Room object
                        // For example, update UI with roomList associated with the selected date
                        updateUI(selectedDayRoom.roomList)


                       /* progressDialog2 = ProgressDialog(applicationContext).apply {
                            setCancelable(false)
                            setMessage("Data Inserting")
                            show()
                        }*/
                        GetFirstdata(selectedDate,selectedDayRoom.roomList)

                        adapter.setInitiallySelectedPosition(0)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case when nothing is selected (optional)
                }
            }



        })
    }

    private fun GetFirstdata(
        selectedDate: String,
        roomList: List<GET_DAYS_RESPONSE.HydraMember.RoomOrder>,

    ) {

        viewModel1.setSelectedDate(selectedDate)

        Log.d(ConstantsApp.TAG,""+selectedDate)

        val sessionsByDate=viewModel1.sessionsByDate

        viewModel1.sessionsByDate.observe(this, Observer {
            response->

            Log.d(ConstantsApp.TAG,"sessionsByDate=>"+response)

            if (roomList.isNotEmpty()) {
                val topElement = roomList[0] // Get the top element (first element) of the list

                // Now you can use the topElement as needed
                // For example, you can access its properties:
                val topElementName = topElement.name
                val topElementDate = topElement.date

                val filteredSessions = response.filter { session ->
                    session.room!!.name == topElementName && session.date == selectedDate
                }.distinctBy { it.title }

                Log.d(ConstantsApp.TAG,"filteredSessions=>"+filteredSessions)


                updateSessionUI(filteredSessions)

                // Or perform any other operation you need
            } else {
                // Handle the case where the roomList is empty
                // For example, show a message or perform another action
            }

        })

       // Log.d(ConstantsApp.TAG,"sessionsByDate=>"+sessionsByDate)



      /*  viewModel1.getAll_Session.observe(this, Observer {
                response->
            Log.d(ConstantsApp.TAG,"getAll_Session=>"+response)





            if (roomList.isNotEmpty()) {
                val topElement = roomList[0] // Get the top element (first element) of the list

                // Now you can use the topElement as needed
                // For example, you can access its properties:
                val topElementName = topElement.name
                val topElementDate = topElement.date

                val filteredSessions = response.filter { session ->
                    session.room.name == topElementName && session.date == selectedDate
                }.distinctBy { it.title }

                Log.d(ConstantsApp.TAG,"filteredSessions=>"+filteredSessions)
                adapter.setInitiallySelectedPosition(0)

                updateSessionUI(filteredSessions)

                // Or perform any other operation you need
            } else {
                // Handle the case where the roomList is empty
                // For example, show a message or perform another action
            }
        })*/
    }

    private fun updateUI(roomList: List<GET_DAYS_RESPONSE.HydraMember.RoomOrder>) {

        recyclerView = findViewById(R.id.RecyclerView_Room)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = RoomAdapter(roomList,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

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


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun getScientificProgram(
        roomName: GET_DAYS_RESPONSE.HydraMember.RoomOrder,
        position: Int,
        view: View
    ) {
       Log.d(ConstantsApp.TAG,"Clicked room=>"+roomName.name)
        Log.d(ConstantsApp.TAG,"selectedDate=>"+selectedDate)
        adapter.setInitiallySelectedPosition(position)
        adapter.notifyDataSetChanged()

        viewModel1.setSelectedDate(selectedDate)

        viewModel1.sessionsByDate.observe(this, Observer {
                response->

            val filteredSessions = response.filter { session ->
                session.room!!.name == roomName.name && session.date == selectedDate
            }.distinctBy { it.title }

            Log.d(ConstantsApp.TAG,"filteredSessions=>"+filteredSessions)

            updateSessionUI(filteredSessions)

        })

       /* viewModel1.getAll_Session.observe(this, Observer {
            response->
            Log.d(ConstantsApp.TAG,"getAll_Session=>"+response)

            val filteredSessions = response.filter { session ->
                session.room.name == roomName.name && session.date == selectedDate
            }.distinctBy { it.title }

            Log.d(ConstantsApp.TAG,"filteredSessions=>"+filteredSessions)

            updateSessionUI(filteredSessions)
        })*/
    }



   /* private fun updateSessionUI(filteredSessions: List<Get_Session>) {

        try {

            Log.d(ConstantsApp.TAG,""+filteredSessions)

            for ( data in filteredSessions)
            {
                Log.d(ConstantsApp.TAG,"title=>"+data.title)
            }
            //val uniqueFilteredSessions = filteredSessions.toSet().toList()

           // Log.d(ConstantsApp.TAG,"uniqueFilteredSessions=>"+uniqueFilteredSessions)


            recyclerViewSession = findViewById(R.id.RecyclerView_session)
            recyclerViewSession.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter1 = SessionAdapter1(filteredSessions,this)



            recyclerViewSession.adapter = adapter1
            adapter1.notifyDataSetChanged()
            // adapter1.updateSessions(newFilteredSessions)

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }





    }*/

    private fun updateSessionUI(filteredSessions: List<Session>) {

        try {

            Log.d(ConstantsApp.TAG,""+filteredSessions)

            for ( data in filteredSessions)
            {
                Log.d(ConstantsApp.TAG,"title=>"+data.title)
            }
            //val uniqueFilteredSessions = filteredSessions.toSet().toList()

            // Log.d(ConstantsApp.TAG,"uniqueFilteredSessions=>"+uniqueFilteredSessions)


            recyclerViewSession = findViewById(R.id.RecyclerView_session)
            recyclerViewSession.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter1 = SessionAdapter1(filteredSessions,this)



            recyclerViewSession.adapter = adapter1
            adapter1.notifyDataSetChanged()
            // adapter1.updateSessions(newFilteredSessions)

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }





    }

    override fun getSessionCall(data: Session, position: Int, view: View) {
       Log.d(ConstantsApp.TAG,"getSessionCall=>"+data.program_points)


        sessionManager.saveSession(data)


        val intent1 = Intent(this, TopicActivity::class.java).apply {
            //putExtra("sessionData", data) // Assuming sessionData is an instance of Get_Session
        }
        startActivity(intent1)



       /* val intent = Intent(this, TopicActivity::class.java).apply {
            putExtra("sessionID", data.id)
            putExtra("sessionName", data.timeslotTitle ?: "Plenary")
            putExtra("title", data.title)
            putParcelableArrayListExtra("programPoints",data.programPoints)

            val formattedDate = ConstantsApp.getFormattedDate(data.date)
            putExtra("date", formattedDate)

            data.room?.let { room ->
                putExtra("roomName", room.name)
            }

            putExtra("time", ConstantsApp.formatTime(data.begin) + " - " + ConstantsApp.formatTime(data.end))
        }
        startActivity(intent)*/
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {

            R.id.imageFilter->
            {
                showFilterPopWindow()
            }
            R.id.Event_backbtn->
            {
                val intent=Intent(this,DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showFilterPopWindow() {
        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(com.org.wfnr_2024.R.layout.custom_popup_layout, null)
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)
        val RecyclerView_filterData:RecyclerView=dialogView.findViewById(R.id.RecyclerView_filterData)
        val ImageView_close:ImageView=dialogView.findViewById(R.id.ImageView_close)
        val EditText_Search_FilterData:EditText=dialogView.findViewById(R.id.EditText_Search_FilterData)


        ImageView_close.setOnClickListener {
            dialog.dismiss()
        }

        setFilterData(RecyclerView_filterData)

        EditText_Search_FilterData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }

            override fun afterTextChanged(s: Editable?) {
                // Filter data based on the entered text
                filterData(s.toString(),RecyclerView_filterData)
            }
        })




        // Set dialog window properties for centering

        // Set dialog window properties for centering
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            window.setGravity(Gravity.CENTER)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()
    }

    private fun setFilterData(recyclerviewFilterdata: RecyclerView) {

       /* viewModel1.getAll_FilterData.observe(this, Observer {
            filterlist->
            Log.d(ConstantsApp.Filter,"filter Data=>"+filterlist)
            (recyclerviewFilterdata.adapter as? FilterDataAdapter)?.notifyDataSetChanged()

            recyclerviewFilterdata.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
           val adapter = FilterDataAdapter(filterlist,this)
            recyclerviewFilterdata.adapter = adapter
            adapter.notifyDataSetChanged()
        })*/

        viewModel1.get_All_Session.observe(this, Observer {

            response->


            Log.d(ConstantsApp.TAG,"chair and speaker name=>"+ConstantsApp.getChairAndSpeakerNames(response))

            val (chairNames, speakerNames) = ConstantsApp.getChairAndSpeakerNames(response)

            // Use a set to avoid duplication
            val uniqueChairNames = chairNames.toSet()
            val uniqueSpeakerNames = speakerNames.toSet()

            val displayItems = mutableListOf<DisplayItem>()
            uniqueChairNames.forEach { name ->
                displayItems.add(DisplayItem(name, ItemType.CHAIR))
            }
            uniqueSpeakerNames.forEach { name ->
                displayItems.add(DisplayItem(name, ItemType.SPEAKER))
            }
            val adapter = FilterDataAdapter(displayItems, this)
            recyclerviewFilterdata.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerviewFilterdata.adapter = adapter
            adapter.notifyDataSetChanged()

        })

    }

    private fun filterData(query: String, RecyclerView_filterData: RecyclerView) {
        // Filter data based on the query and update RecyclerView
     /* viewModel1.getFilteredData(query).observe(this, Observer {
              filterlist->
           Log.d(ConstantsApp.Filter,"filter data with character=>"+filterlist)

          (RecyclerView_filterData.adapter as? FilterDataAdapter)?.notifyDataSetChanged()

          RecyclerView_filterData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
          val adapter = FilterDataAdapter(filterlist,this)
          RecyclerView_filterData.adapter = adapter
          //adapter.notifyDataSetChanged()
      })*/

        viewModel1.get_All_Session.observe(this, Observer {

                response->


            Log.d(ConstantsApp.TAG,"chair and speaker name=>"+ConstantsApp.getChairAndSpeakerNames(response))

            val (chairNames, speakerNames) = ConstantsApp.getChairAndSpeakerNames(response)

            val displayItems = mutableListOf<DisplayItem>()
            chairNames.filter { it.contains(query, ignoreCase = true) }
                .forEach { name ->
                    displayItems.add(DisplayItem(name, ItemType.CHAIR))
                }

            speakerNames.filter { it.contains(query, ignoreCase = true) }
                .forEach { name ->
                    displayItems.add(DisplayItem(name, ItemType.SPEAKER))
                }

            Log.d(ConstantsApp.TAG,"displayItems=>"+displayItems)
            val uniqueDisplayItems = displayItems.distinctBy { it.name }
            val sortedDisplayItems = uniqueDisplayItems.sortedBy { it.name }

            val adapter = FilterDataAdapter(sortedDisplayItems, this)
            RecyclerView_filterData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            RecyclerView_filterData.adapter = adapter
            adapter.notifyDataSetChanged()

        })


    }

    @SuppressLint("MissingInflatedId")
    override fun getFilterClickedData(data: DisplayItem, position: Int, view: View) {
     //  Log.d(ConstantsApp.Filter,"getFilterClickedData=>"+data)


        val dialog = Dialog(this)
        // Inflate the custom layout
        // Inflate the custom layout
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.custom_bottom_popup_layout, null)
        val TextView_name:TextView=dialogView.findViewById(R.id.TextView_name)

        val RecyclerView_Session_Topic:RecyclerView=dialogView.findViewById(R.id.RecyclerView_Session_Topic)

        GetSessionData(data.name,RecyclerView_Session_Topic)

        TextView_name.text=data.name


        TextView_name.setOnClickListener {
            dialog.dismiss()
        }
        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)






      /*  if (data.title.equals(null))
        {
            TextView_name.text=data.first_name+" "+data.last_name
        }
        else{
            TextView_name.text=data.title+" "+data.first_name+" "+data.last_name
        }*/


        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.attributes.windowAnimations = R.style.DialogAnimation
            window!!.setGravity(Gravity.BOTTOM)
        }

        // Show the dialog

        // Show the dialog
        dialog.show()


    }

    private fun GetSessionData(
        data: String?,
        RecyclerView_Session_Topic: RecyclerView
    ) {

        val clicked_id=data

        Log.d(ConstantsApp.Session,"clicked id=>"+clicked_id)

        ChairSpeakerList!!.clear()
       /* viewModel1.getAll_Session.observe(this, Observer {
            response->

            for (data in response)
           {
               val type=data.type
               val date=data.date

               val chairs=data.chairs
               val room=data.room
               val programPoints=data.programPoints
               val room_id1=room.id1
               var start_time=data.begin
               var end_time=data.end
               var sessio_topic_timeslotTitle=data.timeslotTitle
               var sessio_topic_title=data.title




               for (chairs_data in chairs)

               {
                   val id=chairs_data.person.id1
                   val chair_type=chairs_data.type1

                  // if (clicked_id.equals(id) && clicked_id.equals(room_id1))

                   if (clicked_id.equals(id))
                   {
                       val id=chairs_data.person.id1
                       val title=chairs_data.person.title
                       val firstName=chairs_data.person.firstName
                       val lastName=chairs_data.person.lastName
                       val type=chairs_data.person.type

                       val person_name=firstName+" "+lastName


                       val data=ChairSpeakerList(id, chairs_data.type, title, date,start_time,end_time,person_name,room.name,sessio_topic_timeslotTitle,sessio_topic_title)



                       ChairSpeakerList!!.add(data)



                   }



               }


               for (programPoints_data in programPoints)
               {
                   val speakers_data=programPoints_data.speakers

                   for (speakers in speakers_data)
                   {
                       val person=speakers.person
                       val speaker_type=speakers.type
                       val id=person.id1
                       val title=person.title
                       val firstName=person.firstName
                       val lastName=person.lastName
                       val type=person.type

                       val person_name=firstName+" "+lastName
                       if (clicked_id.equals(id))
                       {
                           val data=ChairSpeakerList(id, speaker_type, title, programPoints_data.date,programPoints_data.begin,programPoints_data.end,person_name,room.name,sessio_topic_timeslotTitle,programPoints_data.primaryTitle)

                           ChairSpeakerList!!.add(data)

                       }


                   }


               }



           }


          //  Log.d(ConstantsApp.TAG,"ChairSpeakerList=>"+ChairSpeakerList)


            // Assuming you have your list of ChairSpeakerList objects
            val chairList = ChairSpeakerList!!.filter { it.type == "Chair" }
            val speakerList = ChairSpeakerList!!.filter { it.type == "Speaker" }

// Combine the two lists
            val combinedList = mutableListOf<ChairSpeakerList>()
            combinedList.addAll(chairList)
            combinedList.addAll(speakerList)

            //Log.d(ConstantsApp.TAG,"combinedList=>"+combinedList)


            val distinctList = combinedList.distinctBy { it.createKey() }

            Log.d(ConstantsApp.Session,"size=>"+distinctList.size)

            RecyclerView_Session_Topic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = Filter_Session_Topic_Adapter(this,distinctList)
            RecyclerView_Session_Topic.adapter = adapter
            adapter.notifyDataSetChanged()



        })*/

        viewModel1.get_All_Session.observe(this, Observer {
                response->


            var date=""

            for (data in response)
            {
                val type=data.type
                date= data.date!!

                val chairs=data.chairs
                val room=data.room
                val programPoints=data.program_points
                val room_id1=room!!.id
                var start_time=data.begin
                var end_time=data.end
                var sessio_topic_timeslotTitle=data.timeslotTitle
                var sessio_topic_title=data.title




                for (chairs_data in chairs!!)

                {
                    //val id=chairs_data.person.id
                    val id=chairs_data.person.title+" "+chairs_data.person.firstName+" "+chairs_data.person.lastName
                    val chair_type="Chair"

                    // if (clicked_id.equals(id) && clicked_id.equals(room_id1))

                    if (clicked_id!!.contains(id))
                    {
                        val id=chairs_data.person.id
                        val title=chairs_data.person.title
                        val firstName=chairs_data.person.firstName
                        val lastName=chairs_data.person.lastName
                        val type="Chair"

                        val person_name=firstName+" "+lastName


                        val data=ChairSpeakerList(id, type, title, date,start_time,end_time,person_name,room.name,sessio_topic_timeslotTitle,sessio_topic_title)



                        ChairSpeakerList!!.add(data)



                    }



                }


                for (programPoints_data in programPoints!!)
                {
                    val speakers_data=programPoints_data.speakers

                    for (speakers in speakers_data)
                    {
                        val person=speakers.person
                        val speaker_type="Speaker"
                        //val id=person!!.id
                        val id=person!!.title+" "+person.firstName+" "+person.lastName
                        val title=person.title
                        val firstName=person.firstName
                        val lastName=person.lastName
                        val type="Speaker"

                        val person_name=firstName+" "+lastName
                        if (clicked_id!!.contains(id))
                        {
                            val data=ChairSpeakerList(id, speaker_type, title, date,programPoints_data.begin,programPoints_data.end,person_name,room.name,sessio_topic_timeslotTitle,programPoints_data.primaryTitle)

                            ChairSpeakerList!!.add(data)

                        }


                    }


                }



            }


            //  Log.d(ConstantsApp.TAG,"ChairSpeakerList=>"+ChairSpeakerList)


            // Assuming you have your list of ChairSpeakerList objects
            val chairList = ChairSpeakerList!!.filter { it.type == "Chair" }
            val speakerList = ChairSpeakerList!!.filter { it.type == "Speaker" }

// Combine the two lists
            val combinedList = mutableListOf<ChairSpeakerList>()
            combinedList.addAll(chairList)
            combinedList.addAll(speakerList)

            //Log.d(ConstantsApp.TAG,"combinedList=>"+combinedList)


            val distinctList = combinedList.distinctBy { it.createKey() }

            Log.d(ConstantsApp.Session,"size=>"+distinctList.size)

            RecyclerView_Session_Topic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val adapter = Filter_Session_Topic_Adapter(this,distinctList)
            RecyclerView_Session_Topic.adapter = adapter
            adapter.notifyDataSetChanged()



        })



    }

    fun ChairSpeakerList.createKey(): String {
        return "$date-$sessio_topic_title-$start_time-$end_time-$room_name"
    }


}
