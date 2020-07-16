//
// Created by cl on 2020/7/16.
//
#include "shape.h"
#include "../ggl.h"
#include "../utils.h"


#ifndef MY_APPLICATION_TRIANGLE_H
#define MY_APPLICATION_TRIANGLE_H


class Triangle : public Shape {
private:

    GLuint mProgram;
    GLint mPositionHandle;

    GLint mColorHandle;

    GLfloat *color;

    GLuint VBO;

    GLuint vertShader;

    GLuint fragShader;

    GLuint VAO;

public:
    Triangle();

    void initOpenGL() override;

    void resize() override;

    void draw() override;

};

#endif //MY_APPLICATION_TRIANGLE_H
