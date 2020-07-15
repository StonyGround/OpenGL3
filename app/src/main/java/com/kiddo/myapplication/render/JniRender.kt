package com.kiddo.myapplication.render

import android.opengl.GLSurfaceView
import com.kiddo.myapplication.Native
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class JniRender :GLSurfaceView.Renderer{


    override fun onDrawFrame(gl: GL10?) {
        Native.renderOneFrame()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Native.onViewportChanged(width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Native.initOpenGL()
    }

}