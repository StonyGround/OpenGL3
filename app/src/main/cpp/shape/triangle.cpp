//
// Created by cl on 2020/7/15.
//
#include "triangle.h"

//顶点
GLfloat vVertices[] = {0.5f, 0.5f, 0.0f,//右上
                       0.5f, -0.5f, 0.0f,//右下
                       -0.5f, -0.5f, 0.0f//左下
};

//颜色
GLfloat c[] = {1.0f, 0.0f, 1.0f, 1.0f};

void Triangle::initOpenGL() {
    //创建着色器
    char *vertCode = loadFileContent("triangle.vert");
    char *fragCode = loadFileContent("triangle.frag");
    vertShader = loadShader(GL_VERTEX_SHADER, vertCode);
    fragShader = loadShader(GL_FRAGMENT_SHADER, fragCode);

    //创建program
    mProgram = createProgram(vertShader, fragShader);
    //删除着色器
    glDeleteShader(vertShader);
    glDeleteShader(fragShader);

    //生成一个VAO对象
    glGenVertexArrays(1, &VAO);
    //生成一个VBO对象
    glGenBuffers(1, &VBO);

    //注册绑定VAO，将VBO绑定到VAO中，以后绘制只需要切换VAO就可以
    glBindVertexArray(VAO);

    //绑定VBO
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    //把顶点数据复制到VBO中
    glBufferData(GL_ARRAY_BUFFER, sizeof(vVertices), vVertices, GL_STATIC_DRAW);

    mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
    LOGD("mPositionHandle----%d", mPositionHandle);
    glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat),
                          (GLvoid *) nullptr);
    glEnableVertexAttribArray(0);

    //调用glVertexAttribPointer后可解绑VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    //解绑VAO
    glBindVertexArray(0);
}


void Triangle::draw() {
    LOGD("draw");

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(mProgram);

    //矩阵转换
//    mMatrixHandler = glGetUniformLocation(mProgram, "vMatrix");
    //指定vMatrix的值
//    glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);

    //启用三角形顶点的句柄
//    glEnableVertexAttribArray(mPositionHandle);
//    //准备三角形的坐标数据
//    glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, GL_FALSE, 0, vVertices);

    glBindVertexArray(VAO);


    //获取片元着色器的vColor成员的句柄
    mColorHandle = glGetUniformLocation(mProgram, "vColor");
    //设置绘制三角形的颜色
    color = c;
    glUniform4fv(mColorHandle, 1, color);
//        glUniform4f(mColorHandle, 1.0f, 0.0f, 1.0f, 1.0f);

    //顶点着色
//    mColorHandle = glGetAttribLocation(mProgram, "aColor");
//    glEnableVertexAttribArray(mColorHandle);
//    glVertexAttribPointer(mColorHandle, 4, GL_FLOAT, GL_FALSE, 0, aColor);

    glDrawArrays(GL_TRIANGLES, 0, 3);

    glBindVertexArray(0);
    //禁止顶点数组的句柄
//    glDisableVertexAttribArray(mPositionHandle);
}

void Triangle::resize() {

}

Triangle::Triangle() = default;
