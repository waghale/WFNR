package com.org.wfnr_2024.Activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.PermissionRequest
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

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

        requestPermission()

    }

    private fun requestPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
               /* Manifest.permission.CAMERA,*/
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,


                )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    // Check if all permissions are granted
                    if (report != null && report.areAllPermissionsGranted()) {

                        Log.d("mytag","Check if all permissions are granted")
                        gotoscreen()

                        // All permissions granted, you can proceed with your logic
                    } else {
                        Log.d("mytag"," At least one permission is denied")
                        // At least one permission is denied


                        //checkForAppUpdate()
                       gotoscreen()

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                }


            })
            .check()
    }

    private fun gotoscreen() {
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
