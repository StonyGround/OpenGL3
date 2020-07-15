package com.kiddo.myapplication.render

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.kiddo.myapplication.Native

class MySurfaceView(context: Context?, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {
    init {
        setEGLContextClientVersion(3)
        val renderer = JniRender()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY

        Native.initAssetManager(context!!.assets)
    }
}