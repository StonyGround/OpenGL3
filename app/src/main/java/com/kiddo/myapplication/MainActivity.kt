package com.kiddo.myapplication

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

//        PermissionUtils.permission(PermissionConstants.STORAGE)
//            .callback(object : PermissionUtils.SimpleCallback {
//                override fun onGranted() {
//
//                }
//
//                override fun onDenied() {
//
//                }
//            }).request()

        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val path = externalFilesDir!!.absolutePath + "/123.txt"
//        val path = "data/123.txt"
        val native = Native()

        // Example of a call to a native method
//        sample_text.text = stringFromJNI()
    }


}
