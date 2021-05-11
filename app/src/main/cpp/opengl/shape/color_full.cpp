//
// Created by cl on 2020/7/17.
//

#include "color_full.h"


void RectangleColorFull::initOpenGL() {
    GLfloat vVertices[] = {
            // 位置              // 颜色
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,   // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,   // 左下
            0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f    // 顶部
    };

    //创建着色器
    char *vertCode = loadFileContent("color_full.vert");
    char *fragCode = loadFileContent("color_full.frag");
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

    //可以直接指定顶点位置，也可以通过glGetAttribLocation获取
    mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
    LOGD("mPositionHandle----%d", mPositionHandle);
    glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(GLfloat),
                          (GLvoid *) nullptr);
    glEnableVertexAttribArray(mPositionHandle);


    //颜色属性
    //    由于我们现在有了两个顶点属性，我们不得不重新计算步长值。
    //    为获得数据队列中下一个属性值（比如位置向量的下个x分量）我们必须向右移动6个float，其中3个是位置值，另外3个是颜色值。
    //    这使我们的步长值为6乘以float的字节数（=24字节）。
    //    同样，这次我们必须指定一个偏移量。对于每个顶点来说，位置顶点属性在前，所以它的偏移量是0。
    //    颜色属性紧随位置数据之后，所以偏移量就是3 * sizeof(GLfloat)，用字节来计算就是12字节。
    mColorHandle = glGetAttribLocation(mProgram, "color");
    LOGD("mPositionHandle----%d", mColorHandle);
    glVertexAttribPointer(mColorHandle, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(GLfloat),
                          (GLvoid *) (3 * sizeof(GLfloat)));
    glEnableVertexAttribArray(mColorHandle);


    //调用glVertexAttribPointer后可解绑VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    //解绑VAO
    glBindVertexArray(0);
}


void RectangleColorFull::draw() {

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(mProgram);

    glBindVertexArray(VAO);

    glDrawArrays(GL_TRIANGLES, 0, 3);

    glBindVertexArray(0);
}

void RectangleColorFull::resize() {

}

RectangleColorFull::RectangleColorFull() = default;