//
// Created by cl on 2020/7/17.
//

//
// Created by cl on 2020/7/15.
//
#include "texture2d.h"

void Texture2D::initOpenGL() {
    //顶点
    GLfloat vertices[] = {
            //---- 位置 ----   ---- 颜色 ----     - 纹理坐标 -
            0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,   // 右上
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,   // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,   // 左下
            -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f    // 左上
    };

    GLuint indices[] = {  // Note that we start from 0!
            0, 1, 3, // First Triangle
            1, 2, 3  // Second Triangle
    };

    //创建着色器
    char *vertCode = loadFileContent("texture2d.vert");
    char *fragCode = loadFileContent("texture2d.frag");
    GLuint vertShader = loadShader(GL_VERTEX_SHADER, vertCode);
    GLuint fragShader = loadShader(GL_FRAGMENT_SHADER, fragCode);

    //创建program
    mProgram = createProgram(vertShader, fragShader);

    GLuint VBO, EBO;

    glGenVertexArrays(1, &VAO);
    glGenBuffers(1, &VBO);
    glGenBuffers(1, &EBO);

    //注册绑定VAO，将VBO绑定到VAO中，以后绘制只需要切换VAO就可以
    glBindVertexArray(VAO);

    //绑定VBO
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
    //绑定EBO
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);

    //position
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(GLfloat),
                          (GLvoid *) nullptr);
    glEnableVertexAttribArray(0);
    //color
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(GLfloat),
                          (GLvoid *) (3 * sizeof(GLfloat)));
    glEnableVertexAttribArray(1);
    //texture
    glVertexAttribPointer(2, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(GLfloat),
                          (GLvoid *) (6 * sizeof(GLfloat)));
    glEnableVertexAttribArray(2);

    //调用glVertexAttribPointer后可解绑VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    //解绑VAO
    glBindVertexArray(0);


    //创建纹理
    texture = createTextureFromBMP("test2.bmp");
}


void Texture2D::draw() {
    //颜色
    GLfloat color[] = {1.0f, 0.0f, 1.0f, 1.0f};

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(mProgram);

    glBindTexture(GL_TEXTURE_2D, texture);

    glBindVertexArray(VAO);

    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, nullptr);

    glBindVertexArray(0);
}

void Texture2D::resize() {

}

Texture2D::Texture2D() = default;
