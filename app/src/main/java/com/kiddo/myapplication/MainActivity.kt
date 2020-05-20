package com.kiddo.myapplication

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        createFile(path)

        // Example of a call to a native method
//        sample_text.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun createFile(filepath: String): Int


    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
