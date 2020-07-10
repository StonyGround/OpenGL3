package com.kiddo.myapplication.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import timber.log.Timber
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRender : GLSurfaceView.Renderer {

    init {

    }

    var triangleCoords = floatArrayOf(
        0.5f, 0.5f, 0.0f,  // top
        -0.5f, -0.5f, 0.0f,  // bottom left
        0.5f, -0.5f, 0.0f // bottom right
    )

    override fun onDrawFrame(gl: GL10?) {
        Timber.i("onDrawFrame")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Timber.i("onSurfaceChanged")
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Timber.i("onSurfaceCreated: ")
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

}