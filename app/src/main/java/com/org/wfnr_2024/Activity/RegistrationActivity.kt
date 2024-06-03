package com.org.wfnr_2024.Activity

import Sharepreference_login
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.org.wfnr_2024.Model.Bearer_Token.Bearer_Token_Request
import com.org.wfnr_2024.Model.GET_MEMBER.Get_Member_Response
import com.org.wfnr_2024.Model.Login.Registration_Request
import com.org.wfnr_2024.Model.VerifyParticipent.Verify_Participent_Request
import com.org.wfnr_2024.Model.VerifyParticipent.Verify_Participent_Response
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.ConstantsApp
import com.org.wfnr_2024.Utils.MyValidator
import com.org.wfnr_2024.Utils.SessionManager
import com.org.wfnr_2024.Utils.SessionManager_Login
import com.org.wfnr_2024.ViewModel.ResourceApp
import com.org.wfnr_2024.ViewModel.WFNRProviderFactory
import com.org.wfnr_2024.ViewModel.WFNRRespository
import com.org.wfnr_2024.ViewModel.WFNR_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_DAY_ROOM_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_FILTER_DATA_LOCAL_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DAO.GET_SESSION_DAO
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.ROOM_DATABASE_MODEL.LOGIN.LOGIN_LOCAL
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_Repository
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModel
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_LOCAL_ViewModelFactory
import com.org.wfnr_2024.WFNR_ROOM_DATABASE.WFNR_Room_Database

class RegistrationActivity : AppCompatActivity() ,TextWatcher{

    private lateinit var membershipNoEditText: EditText
    private lateinit var mobEmailEditText: EditText
    private lateinit var editTextMemberName: EditText
    private lateinit var editTextCity: EditText
    private lateinit var editTextCountry: EditText
    private lateinit var loginButton: TextView
    lateinit var Dashboard_back:ImageView
    private val USER_PREFERENCES = "UserPrefs"
    private lateinit var sharedPreferencesManager: Sharepreference_login

    lateinit var viewModel: WFNR_ViewModel
    lateinit var viewModel1: WFNR_LOCAL_ViewModel
    lateinit var sessionManager: SessionManager
    lateinit var sessionManager_Login: SessionManager_Login
    lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        getViewModel()
        createRoomDatabase()

         Dashboard_back = findViewById<ImageView>(R.id.Dashboard_back)
        membershipNoEditText = findViewById(R.id.editTextMemberNo)
        mobEmailEditText = findViewById(R.id.editTextEmailOrPhone)
        editTextMemberName = findViewById(R.id.editTextMemberName)
        editTextCity = findViewById(R.id.editTextCity)
        editTextCountry = findViewById(R.id.editTextCountry)
        loginButton = findViewById(R.id.loginButton)



        sharedPreferencesManager = Sharepreference_login(this)

        Dashboard_back.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source != null && source.length > 0 && source[0] == ' ' && dstart == 0) {
                // Prevent space at the beginning
                ""
            } else {
                null
            }
        }

        // Set the cursor color for Member Number EditText
       /* val cursorColorMemberNo = ContextCompat.getColor(this, R.color.black)
        setEditTextCursorColor(membershipNoEditText, cursorColorMemberNo)*/

        // Set the cursor color for Email or Phone EditText
       /* val cursorColorEmailOrPhone = ContextCompat.getColor(this, R.color.black)
        setEditTextCursorColor(mobEmailEditText, cursorColorEmailOrPhone)*/

        membershipNoEditText.filters = arrayOf(inputFilter)
        mobEmailEditText.filters = arrayOf(inputFilter)

        loginButton.setOnClickListener {

            Log.d("mytag","loginButton clicked"+loginButton)
            // Check for internet connectivity
            if (isOnline()) {
                // Device is online, set the click listener for the login button


               /* val membershipNo = membershipNoEditText.text.toString().trim()
                val mobEmail = mobEmailEditText.text.toString().trim()

                if (membershipNo.isNotEmpty() && mobEmail.isNotEmpty()) {
                    Log.d(
                        "mytag",
                        "User Details: Membership No=$membershipNo, Email/Phone=$mobEmail"
                    )


                        // Perform login only if membershipNo is in uppercase
                        performLogin(membershipNo,mobEmail)
                    }

                else if (membershipNo.isEmpty() && mobEmail.isEmpty()) {
                    // Both membership number and email/phone are empty, display a message
                    showAlert2("Please enter Credentials",)

                }
                else if (membershipNo.isEmpty())
                {
                    showAlert("Please enter Registration No",)

                } else
                {
                    showAlert1("Please enter Email/Phone",)
                }*/


                if (validation())
                {
                    val grantType = "client_credentials"
                    val clientId = "wfnr-app"
                    val clientSecret = "195f464d-6106-48d9-9b71-8be2a54f4670"

                    val Bearer_Token_Request= Bearer_Token_Request(grantType, clientId, clientSecret)

                    viewModel.getToken(Bearer_Token_Request,progressDialog)
                    Bearer_TokenResponse(mobEmailEditText.text.toString())
                }





            }
            else {
                // Device is offline, show the alert directly
                showNoInternetAlert("Please connect to the internet")
            }
        }
        // Set a touch listener for the root layout to dismiss the keyboard when tapped outside EditText fields
        val rootView = findViewById<ScrollView>(R.id.rootlayout)
        // Handle the case when the user clicks outside the EditText fields
        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Hide the keyboard when tapping outside the EditText fields
                hideKeyboard()
                // Clear focus from EditText fields
                mobEmailEditText.clearFocus()
            }
            return@setOnTouchListener false
        }

        membershipNoEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Change the background color when membershipNoEditText is clicked
                membershipNoEditText.setBackgroundResource(R.drawable.editable_background)
                // Change text color and style to green when focused
                membershipNoEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
                membershipNoEditText.setTypeface(null, Typeface.NORMAL)
                // Change hint text color to green
                membershipNoEditText.setHintTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
            } else {
                // Check if the focus is lost and mobEmailEditText has gained focus
                if (!hasFocus && mobEmailEditText.hasFocus()) {
                    // Change text color to dark green and background color to white
                    membershipNoEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.dark_green))
                    membershipNoEditText.setBackgroundResource(R.drawable.background_login)
                } else {
                    // Change the background color back to the original when focus is lost
                    membershipNoEditText.setBackgroundResource(R.drawable.login_bg)
                    // Reset text color and style to default
                    membershipNoEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.black))
                    membershipNoEditText.setTypeface(null, Typeface.NORMAL)
                    // Reset hint text color to default
                    membershipNoEditText.setHintTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
                }
            }
            // Check fields and update button style when focus changes
            checkFieldsAndSetButtonStyle()
        }

        mobEmailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Change the background color when mobEmailEditText is clicked
                mobEmailEditText.setBackgroundResource(R.drawable.editable_background)
                // Change text color and style to green when focused
                mobEmailEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
                mobEmailEditText.setTypeface(null, Typeface.NORMAL)
                // Change hint text color to green
                mobEmailEditText.setHintTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
            } else {
                // Check if the focus is lost and membershipNoEditText has lost focus
                if (!hasFocus && !membershipNoEditText.hasFocus()) {
                    // Change text color to dark green and background color to white
                    mobEmailEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.dark_green))
                    mobEmailEditText.setBackgroundResource(R.drawable.background_login)
                } else {
                    // Change the background color back to the original when focus is lost
                    mobEmailEditText.setBackgroundResource(R.drawable.background_login)
                    // Reset text color and style to default
                    mobEmailEditText.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.dark_green))
                    mobEmailEditText.setTypeface(null, Typeface.NORMAL)
                    // Reset hint text color to default
                    mobEmailEditText.setHintTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.green))
                }
            }
            // Check fields and update button style when focus changes
            checkFieldsAndSetButtonStyle()
        }
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

    private fun Bearer_TokenResponse(text: String) {
        viewModel.getBearerTokenLiveData.observe(this, Observer {
            response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    Log.d(ConstantsApp.TAG,"Access Token=>"+response.data!!.access_token)
                    val Bearer_Token=response.data.access_token

                    sessionManager.saveBearerToken(Bearer_Token)

                    Verify_Participent(text,Bearer_Token)



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

    private fun Verify_Participent(text: String, bearerToken: String) {

        val accept="application/json"
        val authorization="Bearer "+bearerToken
        val eventId="101272151768307"
        val email="jonas.hock@conventus.de"
        //val email=mobEmailEditText.text.toString()
        val participantNumber="994"
       // val participantNumber=membershipNoEditText.text.toString()

        val verify_participent_request=Verify_Participent_Request(accept, authorization, eventId, email, participantNumber)

        viewModel.VerifyParticipent(verify_participent_request,progressDialog)
        VerifyParticipentResponse()


    }

    private fun VerifyParticipentResponse() {
        viewModel.getVerify_Participent_ResponseLiveData.observe(this, Observer {
            response->


            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()
                    try {
                       // Log.d(ConstantsApp.TAG,"verified=>"+response)

                        val responseData: Verify_Participent_Response? = response.data
                        Log.d(ConstantsApp.TAG,"verified=>"+responseData!!.verified)

                        when(responseData.verified)
                        {
                            true->
                            {
                                val fname=editTextMemberName.text.toString()
                                val email=mobEmailEditText.text.toString()
                                val WFNR_reg_no=membershipNoEditText.text.toString()
                                val city=editTextCity.text.toString()
                                val country=editTextCountry.text.toString()
                                val Registration_Request= Registration_Request(fname, email, WFNR_reg_no, city, country)
                               viewModel.store_members_to_server(progressDialog,Registration_Request)
                                RegistrationResponse()

                            }
                            false->
                            {
                                Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    catch (e:Exception)
                    {
                        e.printStackTrace()
                    }

                }
                is ResourceApp.Error->
                {
                    progressDialog.dismiss()

                    val errorMessage = response.msg
                    // Handle the error message here, for example:
                    Log.e(ConstantsApp.TAG, "Error: $errorMessage")


                }
                is ResourceApp.Loading->
                {
                    progressDialog.show()
                }

                else -> {}
            }

        })
    }

    private fun RegistrationResponse() {
       viewModel.getRegistrationResponseLiveData.observe(this, Observer {
           response->

           when(response)
           {
               is ResourceApp.Success->
               {
                   progressDialog.dismiss()
                   Log.d(ConstantsApp.TAG,""+response.data!!.message)
                   Log.d(ConstantsApp.TAG,""+response.data!!.status_code)
                   val status_code=response.data.status_code
                   val message=response.data.message
                   when(status_code)
                   {
                       200->
                       {

                           Get_Member_Data_From_Server()

                       }
                       201->
                       {
                           Get_Member_Data_From_Server()
                       }
                       else->
                       {
                           showAlertDialog(message)
                       }


                   }

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

    private fun Get_Member_Data_From_Server() {
       viewModel.getMember(progressDialog)
        GetMemberResponse()
    }

    private fun GetMemberResponse() {
        viewModel.getMemberLiveData.observe(this, Observer {
            response->

            when(response)
            {
                is ResourceApp.Success->
                {
                    progressDialog.dismiss()

                    try {
                        val responseData: Get_Member_Response? = response.data

                        Log.d(ConstantsApp.TAG,"GetMemberResponse=>"+responseData!!.data)

                        var member_data=responseData.data

                        val email=mobEmailEditText.text.toString()

                        for (member in member_data)
                        {
                            if(email.equals(member.email))
                            {
                                val fname=editTextMemberName.text.toString()
                                val email=mobEmailEditText.text.toString()
                                val WFNR_reg_no=membershipNoEditText.text.toString()
                                val city=editTextCity.text.toString()
                                val country=editTextCountry.text.toString()
                                val registrationDataLocalRequest=LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                                    member.id.toString()
                                )
                                viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                                saveRegistrationDataLocalResponse(member.email)
                            }

                        }


                    }
                    catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
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




            /*val data=response.data!!.data

            val email=mobEmailEditText.text.toString()

            for (member in data)
            {
                if(email.equals(member.email))
                {
                      val fname=editTextMemberName.text.toString()
                          val email=mobEmailEditText.text.toString()
                          val WFNR_reg_no=membershipNoEditText.text.toString()
                          val city=editTextCity.text.toString()
                          val country=editTextCountry.text.toString()
                          val registrationDataLocalRequest=LOGIN_LOCAL(0,fname,WFNR_reg_no,email,city,country,
                              member.id.toString()
                          )
                          viewModel1.saveRegistrationDataLocal(registrationDataLocalRequest)
                          saveRegistrationDataLocalResponse()
                }

            }*/
        })
    }

    private fun saveRegistrationDataLocalResponse(email: String) {
       viewModel1.saveSuccess.observe(this, Observer {
           response->

           Log.d(ConstantsApp.TAG,response.toString())
           when(response)
           {
               "Success"->
               {

                   val keep_login=sessionManager_Login.getKeepLogin()





                   when(keep_login)
                   {
                       true->
                       {

                           viewModel1.getLoginLocalByEmail(email).observe(this, Observer {
                                   response->
                               sessionManager.setMemberId(response!!.member_id!!)
                           })

                           sessionManager.setLogin("1")
                           sessionManager.setUserEmaiID(email)

                           sessionManager_Login.setDirectLogin(true)

                           val intent = Intent(this, DashboardActivity::class.java)
                           startActivity(intent)
                           finish()
                       }
                       false->
                       {
                           viewModel1.getLoginLocalByEmail(email).observe(this, Observer {
                                   response->
                               sessionManager.setMemberId(response!!.member_id!!)
                           })
                           // sessionManager.setLogin("1")
                           sessionManager.setUserEmaiID(email)

                           sessionManager_Login.setKeepLogin(true)

                           val intent = Intent(this, DashboardActivity::class.java)
                           startActivity(intent)
                           finish()
                       }

                       else -> {}
                   }


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
        sessionManager_Login=SessionManager_Login(this)



    }








    private fun showAlert(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.login_alert)

        // Find views in the custom dialog layout
        val notificationTitle: TextView = dialog.findViewById(R.id.notificationTitle)
        val textViewLogoutConfirmation: TextView = dialog.findViewById(R.id.textViewLogoutConfirmation)
        val buttonYes: Button = dialog.findViewById(R.id.buttonYes)
        val buttonNo: Button = dialog.findViewById(R.id.buttonNo)

        // Set notification title and confirmation text
        notificationTitle.text = "Alert"
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

    private fun checkFieldsAndSetButtonStyle() {
        val membershipNoNotEmpty = membershipNoEditText.text.isNotEmpty()
        val mobEmailNotEmpty = mobEmailEditText.text.isNotEmpty()

        if (membershipNoNotEmpty && mobEmailNotEmpty) {
            // Both fields are non-empty, set the background and text color of the login button
            loginButton.setBackgroundResource(R.drawable.button_background1)
            loginButton.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.white))
        } else {
            // Either one or both fields are empty, set the original style of the login button
            loginButton.setBackgroundResource(R.drawable.button_background)
            loginButton.setTextColor(ContextCompat.getColor(this@RegistrationActivity, R.color.white))
        }

        // Reset text style for membershipNoEditText
        if (!membershipNoNotEmpty) {
            membershipNoEditText.setTypeface(null, Typeface.NORMAL)

        }

        // Reset text style for mobEmailEditText
        if (!mobEmailNotEmpty) {
            mobEmailEditText.setTypeface(null, Typeface.NORMAL)
        }
    }

   /* @SuppressLint("SoonBlockedPrivateApi")
    private fun setEditTextCursorColor(editText: EditText, color: Int) {
        try {
            val field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            field.isAccessible = true
            val drawableResId = field.getInt(editText)

            val drawable = ContextCompat.getDrawable(this, drawableResId)
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            val fieldEditor = TextView::class.java.getDeclaredField("mEditor")
            fieldEditor.isAccessible = true
            val editor = fieldEditor.get(editText)

            val fieldCursorDrawable = editor.javaClass.getDeclaredField("mCursorDrawable")
            fieldCursorDrawable.isAccessible = true
            val drawables = arrayOf(drawable, drawable)

            fieldCursorDrawable.set(editor, drawables)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/


    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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



    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // Override back button press
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional, to finish LoginActivity after starting DashboardActivity
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d("mytag", "" + s)

        if (membershipNoEditText.text.isNotEmpty()) {
            membershipNoEditText.setBackgroundResource(R.drawable.editable_background)
        } else {
            membershipNoEditText.setBackgroundResource(R.drawable.login_bg)
        }

        if (mobEmailEditText.text.isNotEmpty()) {
            mobEmailEditText.setBackgroundResource(R.drawable.editable_background)
        } else {
            mobEmailEditText.setBackgroundResource(R.drawable.login_bg)
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun validation():Boolean {
        var flag = true

        if (!MyValidator.isValidField(editTextMemberName)) {
            flag = false
        }

        if (!MyValidator.isValidField(membershipNoEditText)) {
            flag = false
        }
        if (!MyValidator.isValidField(mobEmailEditText)) {
            flag = false
        }
       /* if (!MyValidator.isValidField(binding.editTextMobile!!)) {
            flag = false
        }
        if (!MyValidator.isValidField(binding.editTextCollege!!)) {
            flag = false
        }*/


        return flag
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

        // Set the custom layout to the dialog
        // Set the custom layout to the dialog
        dialog.setContentView(dialogView)


        TextView_okay.setOnClickListener {
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
}
