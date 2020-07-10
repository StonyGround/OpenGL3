package com.kiddo.myapplication.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.kiddo.myapplication.util.CommonUtils
import timber.log.Timber
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRender : GLSurfaceView.Renderer {

    private var vertex: String
    private var fragment: String
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0

    private var mProgram: Int = 0

    private val COORDS_PER_VERTEX = 3

    //顶点之间的偏移量
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 每个顶点四个字节

    var triangleCoords = floatArrayOf(
        0.5f, 0.5f, 0.0f,  // top
        -0.5f, -0.5f, 0.0f,  // bottom left
        0.5f, -0.5f, 0.0f // bottom right
    )

    //顶点个数
    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX

    //设置颜色，依次为红绿蓝和透明通道
    var color = floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f)

    private var vertexBuffer: FloatBuffer

    init {

        //申请底层空间
        val bb: ByteBuffer = ByteBuffer.allocateDirect(
            triangleCoords.size * 4
        )
        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)

        fragment = CommonUtils.uRes("triangle.frag")
        vertex = CommonUtils.uRes("triangle.vert")

    }


    override fun onDrawFrame(gl: GL10?) {
        Timber.i("onDrawFrame")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        GLES20.glUseProgram(mProgram)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(
            mPositionHandle, COORDS_PER_VERTEX,
            GLES20.GL_FLOAT, false,
            vertexStride, vertexBuffer
        )
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Timber.i("onSurfaceChanged")
        GLES20.glViewport(0, 0, width, height);
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Timber.i("onSurfaceCreated: ")
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f)

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertex)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragment)
        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram()
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader)
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader)
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram)
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        //根据type创建顶点着色器或者片元着色器
        val shader = GLES20.glCreateShader(type)
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }
}