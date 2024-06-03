package com.org.wfnr_2024.Activity


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.org.wfnr_2024.R

class SplashActivity : AppCompatActivity() {


    lateinit var textView: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        textView=findViewById(R.id.textView_Version)

        textView.text="Version "+getAppVersion()

        // Show loader when splash screen starts
        //   showCustomProgressDialog()

        Handler(Looper.getMainLooper()).postDelayed({
            // Hide loader after 3000 milliseconds (3 seconds) and start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun getAppVersion(): String {
        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Unknown"
    }


}
