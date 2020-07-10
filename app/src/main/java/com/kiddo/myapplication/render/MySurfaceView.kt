package com.kiddo.myapplication.render

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class MySurfaceView(context: Context?, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {
    init {
        setEGLContextClientVersion(2)
        val renderer = MyRender()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}