package com.kiddo.myapplication

import android.content.res.AssetManager

public class Native {



    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        external fun initAssetManager(assets: AssetManager)

        external fun initOpenGL()

        external fun onViewportChanged(width: Int, height: Int)

        external fun renderFrame()

    }
}
