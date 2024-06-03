package com.org.wfnr_2024.Activity

import Sharepreference_login
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.org.wfnr_2024.Model.Login.login_response
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() ,TextWatcher{

    private lateinit var membershipNoEditText: EditText
    private lateinit var mobEmailEditText: EditText
    private lateinit var loginButton: TextView
    private val USER_PREFERENCES = "UserPrefs"
    private lateinit var sharedPreferencesManager: Sharepreference_login

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val Dashboard_back = findViewById<ImageView>(R.id.Dashboard_back)
        membershipNoEditText = findViewById(R.id.editTextMemberNo)
        mobEmailEditText = findViewById(R.id.editTextEmailOrPhone)
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
        val cursorColorMemberNo = ContextCompat.getColor(this, R.color.black)
        setEditTextCursorColor(membershipNoEditText, cursorColorMemberNo)

        // Set the cursor color for Email or Phone EditText
        val cursorColorEmailOrPhone = ContextCompat.getColor(this, R.color.black)
        setEditTextCursorColor(mobEmailEditText, cursorColorEmailOrPhone)

        membershipNoEditText.filters = arrayOf(inputFilter)
        mobEmailEditText.filters = arrayOf(inputFilter)

        loginButton.setOnClickListener {

            Log.d("mytag","loginButton clicked"+loginButton)
            // Check for internet connectivity
            if (isOnline()) {
                // Device is online, set the click listener for the login button


                val membershipNo = membershipNoEditText.text.toString().trim()
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
                membershipNoEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
                membershipNoEditText.setTypeface(null, Typeface.NORMAL)
                // Change hint text color to green
                membershipNoEditText.setHintTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
            } else {
                // Check if the focus is lost and mobEmailEditText has gained focus
                if (!hasFocus && mobEmailEditText.hasFocus()) {
                    // Change text color to dark green and background color to white
                    membershipNoEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.dark_green))
                    membershipNoEditText.setBackgroundResource(R.drawable.background_login)
                } else {
                    // Change the background color back to the original when focus is lost
                    membershipNoEditText.setBackgroundResource(R.drawable.login_bg)
                    // Reset text color and style to default
                    membershipNoEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.black))
                    membershipNoEditText.setTypeface(null, Typeface.NORMAL)
                    // Reset hint text color to default
                    membershipNoEditText.setHintTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
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
                mobEmailEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
                mobEmailEditText.setTypeface(null, Typeface.NORMAL)
                // Change hint text color to green
                mobEmailEditText.setHintTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
            } else {
                // Check if the focus is lost and membershipNoEditText has lost focus
                if (!hasFocus && !membershipNoEditText.hasFocus()) {
                    // Change text color to dark green and background color to white
                    mobEmailEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.dark_green))
                    mobEmailEditText.setBackgroundResource(R.drawable.background_login)
                } else {
                    // Change the background color back to the original when focus is lost
                    mobEmailEditText.setBackgroundResource(R.drawable.background_login)
                    // Reset text color and style to default
                    mobEmailEditText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.dark_green))
                    mobEmailEditText.setTypeface(null, Typeface.NORMAL)
                    // Reset hint text color to default
                    mobEmailEditText.setHintTextColor(ContextCompat.getColor(this@LoginActivity, R.color.green))
                }
            }
            // Check fields and update button style when focus changes
            checkFieldsAndSetButtonStyle()
        }
    }

    private fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun showAlert2(message: String) {
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

    @SuppressLint("SuspiciousIndentation")
    private fun showAlert1(message: String) {
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



    private fun performLogin(membershipNo: String, mobEmail: String) {
//        val regNo = "2222" // Example registration number
//        val password = "aihuey.tan@gmail.com" // Example password (considered as email here)

        // Make API call for login
        RetrofitClient1.apiService.login(membershipNo, mobEmail).enqueue(object :
            Callback<login_response> {
            override fun onResponse(call: Call<login_response>, response: Response<login_response>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    // Handle successful login response
                    loginResponse?.let {
                        val message = it.message
                        val data = it.data

                        // Log the message
                        Log.d("mytag", "Login successful: $message")

                        // Log the data received
                        data?.let { userData ->
                            sharedPreferencesManager.saveUserLoggedIn(true)
                            sharedPreferencesManager.saveUserData(userData)
                            // Create an intent to navigate to MyWCNRSection activity
                            val intent = Intent(this@LoginActivity, MyWCNRSection::class.java)

                            // Pass user data as extras to the intent
                            intent.putExtra("userId", userData.id)
                            intent.putExtra("userName", "${userData.title} ${userData.fname} ${userData.lname}")
                            intent.putExtra("userEmail", userData.email)
                            intent.putExtra("userMobile", userData.mobile)
                            intent.putExtra("userRegNo", userData.WFNR_reg_no)
                            intent.putExtra("userCity", userData.city)
                            intent.putExtra("userCountry", userData.country)
                            intent.putExtra("userRegType", userData.reg_type)
                            // Start MyWCNRSection activity and pass success message as an extra
                            intent.putExtra("successMessage", "Login Successful")
                            startActivity(intent)
                            // Finish the current LoginActivity
                            finish()
                        }

                    }
                } else {
                    // Handle unsuccessful login response
                    Log.e("mytag", "Login failed: ${response.code()}")
                    // Show appropriate error message to the user
                    showAlert("Login failed. Please try again.")
                }
            }

            override fun onFailure(call: Call<login_response>, t: Throwable) {
                // Handle network failure
                Log.e("mytag", "Network error: ${t.message}")
                // Show appropriate error message to the user
                showAlert("Network error. Please check your internet connection.")
            }
        })
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
            loginButton.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
        } else {
            // Either one or both fields are empty, set the original style of the login button
            loginButton.setBackgroundResource(R.drawable.button_background)
            loginButton.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
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

    @SuppressLint("SoonBlockedPrivateApi")
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
    }


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
}
