package com.org.wfnr_2024.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.org.wfnr_2024.Fragment.HomeFragment
import com.org.wfnr_2024.Fragment.MenuFragment
import com.org.wfnr_2024.Fragment.NotificationFragment
import com.org.wfnr_2024.Fragment.SettingFragment
import com.org.wfnr_2024.Model.token.TokenResponse
import com.org.wfnr_2024.R
import com.org.wfnr_2024.Utils.RetrofitClient1
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.org.wfnr_2024.Room_Database.ROOM_DAO.Days_DAO
import com.org.wfnr_2024.Room_Database.ROOM_DAO.SessionDataClass_DAO
import com.org.wfnr_2024.Room_Database.WFNR_Repository
import com.org.wfnr_2024.Room_Database.WFNR_Room_Database
import com.org.wfnr_2024.Room_Database.WFNR_ViewModel
import com.org.wfnr_2024.Room_Database.WFNR_ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var imageViewMenu: ImageView
    private var deviceToken: String? = null
    lateinit var bottomNav : BottomNavigationView
    private var privacyPolicyDialog: AlertDialog? = null
    private lateinit var sharedPreferences: SharedPreferences
    var sharedpreferences: SharedPreferences? = null
    private lateinit var appUpdateManager: AppUpdateManager

    lateinit var viewModel1: WFNR_ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        if (!hasUserAcceptedPrivacyPolicy()) {
            showPrivacyPolicyDialog()
        } else {
            // User has already accepted, proceed with the app's main functionality
            // Add code to start your app's main functionality
        }
        appUpdateManager = AppUpdateManagerFactory.create(this)

        checkForAppUpdates()

        imageViewMenu = findViewById(R.id.Wcnrbtn)

        FirebaseApp.initializeApp(this)


        // Retrieve FCM token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Assign Firebase token to class-level property
                    deviceToken = task.result

                    Log.d("mytag", "FCM Token: $deviceToken")
                    // Handle the token (e.g., send it to your server)
                    deviceToken?.let { sendToken(it) }
                } else {
                    Log.e("mytag", "Failed to retrieve FCM token", task.exception)
                }
            }



        imageViewMenu.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }



    loadFragment(HomeFragment())
    bottomNav = findViewById(R.id.bottomNavigationn) as BottomNavigationView
    bottomNav.setOnItemSelectedListener {
        when (it.itemId) {
            R.id.home -> {
                loadFragment(HomeFragment())
                true
            }
            R.id.message -> {


                loadFragment(NotificationFragment())
                true
            }
            R.id.settings -> {
                loadFragment(SettingFragment())
                true
            }
            R.id.menu -> {
                loadFragment(MenuFragment())
                true
            }

            else -> {
                  false
            }
        }
    }
}

    private fun checkForAppUpdates(){
        val appUpdateManager = AppUpdateManagerFactory.create(this)

// Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
            }
        }

        appUpdateInfoTask.addOnFailureListener {exception->

            Log.d("mytag"," appUpdateInfoTask.addOnFailureListener =>"+exception.message)
        }

    }
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
        // handle callback
        if (result.resultCode != RESULT_OK) {
            Log.d("mytag","Update flow failed! Result code: " + result.resultCode);
            // If the update is canceled or fails,
            // you can request to start the update again.
        }
    }



    private fun hasUserAcceptedPrivacyPolicy(): Boolean {
        // Retrieve the user's acceptance status from SharedPreferences
        return sharedPreferences.getBoolean(KEY_ACCEPTED_PRIVACY_POLICY, false)
    }

    private fun setUserAcceptedPrivacyPolicy() {
        // Save the user's acceptance status in SharedPreferences
        sharedPreferences.edit().putBoolean(KEY_ACCEPTED_PRIVACY_POLICY, true).apply()
    }

    companion object {
        private const val KEY_ACCEPTED_PRIVACY_POLICY = "accepted_privacy_policy"
    }

    @SuppressLint("MissingInflatedId")
    private var isPrivacyPolicyAccepted = false

    private fun showPrivacyPolicyDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_privacy_policy, null)

        // Initialize your dialog views
        val textPrivacyPolicy: TextView = view.findViewById(R.id.textprivacypolicy)
        val btnAcceptContinue: TextView = view.findViewById(R.id.acceptButton)

        // Set privacy policy text

        // Set click listener for the Accept & Continue button
        btnAcceptContinue.setOnClickListener {
            // Set the flag to true to indicate that the user has accepted
            setUserAcceptedPrivacyPolicy()
            Log.d("mytag","isPrivacyPolicyAccepted=>true"+isPrivacyPolicyAccepted)
            // Close the dialog
            privacyPolicyDialog?.dismiss()
            // Add code to start your app's main functionality
        }

        builder.setView(view)
        privacyPolicyDialog = builder.create()

        // Disable outside touch dismissal
        privacyPolicyDialog?.setCancelable(false)


        // Set top and bottom margins for the dialog window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(privacyPolicyDialog?.window?.attributes)

        // Set top margin in pixels
        val topMarginInPixels = resources.getDimensionPixelSize(R.dimen.margin_80dp)
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        layoutParams.y = topMarginInPixels

        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        privacyPolicyDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        privacyPolicyDialog?.window?.attributes = layoutParams

        // Set a listener for the back button to handle the back press
        privacyPolicyDialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.action == android.view.KeyEvent.ACTION_UP) {
                // Handle back button press here
                // For example, show a toast indicating that the user needs to accept the policy
                return@setOnKeyListener true
            } else {
                false
            }
        }
        privacyPolicyDialog?.show()
    }
    override fun onResume() {
        super.onResume()
        // Ensure to check for app updates when the activity resumes
       // checkForAppUpdates()
       createRoomDatabase()
    }

    private fun createRoomDatabase() {
        val database = WFNR_Room_Database.getDatabase(applicationContext)

        val SessionDataClass_DAO : SessionDataClass_DAO =database.SessionDataClass_DAO()
        val Days_DAO : Days_DAO =database.Days_DAO()


        val repository = WFNR_Repository(SessionDataClass_DAO,Days_DAO,database)

        viewModel1 = ViewModelProvider(this, WFNR_ViewModelFactory(repository)).get(WFNR_ViewModel::class.java)
    }

    private  fun loadFragment(fragment: Fragment){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.relativelayout_container,fragment)
    transaction.commit()
}



    override fun onBackPressed() {
        // Check if the privacy policy dialog is showing
        if (privacyPolicyDialog?.isShowing == true) {
            // Optionally, show a toast to inform the user to accept the policy
            // Toast.makeText(this, "Please accept the privacy policy", Toast.LENGTH_SHORT).show()
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            // If the back stack is empty, show exit confirmation dialog
            showExitConfirmationDialog()
        } else {
            // Otherwise, proceed with the default back press behavior
            super.onBackPressed()
        }
    }

    private fun showExitConfirmationDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.exit_app)
        // Find views in the custom dialog layout
        val confirmationText: TextView = dialog.findViewById(R.id.textViewLogoutConfirmation)
        val buttonYes: Button = dialog.findViewById(R.id.buttonYes)
        val buttonNo: Button = dialog.findViewById(R.id.buttonNo)
        // Set confirmation text
        confirmationText.text = "Do you want to exit the app?"
        buttonYes.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
        buttonNo.setOnClickListener {
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


}

    private fun sendToken(deviceToken: String) {
        val deviceType = "Android"
        val deviceModel = "Samsung Galaxy S21"
        val deviceOS = "Android 12"
        val location = "Mumbai"
        val imeiNo = "123456789012345"
        // Make API call to generate token
        val call = RetrofitClient1.apiService.generateToken(
            deviceType,
            deviceModel,
            deviceOS,
            location,
            imeiNo,
            deviceToken
        )

        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    if (tokenResponse != null) {
                        val token = tokenResponse.message
                        Log.d("mytag", "Token: $token")
                    } else {
                        Log.e("mytag", "Token response body is null")
                    }
                } else {
                    Log.e("mytag", "Failed to retrieve token: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            }

        })
    }







