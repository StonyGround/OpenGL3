//
// Created by cl on 2020/7/17.
//
#include "../base_gl.h"
#include "../ggl.h"
#include "../utils.h"

#ifndef MY_APPLICATION_COLOR_FULL_H
#define MY_APPLICATION_COLOR_FULL_H
class RectangleColorFull : public IOpenGL {
private:

    GLuint mProgram;
    GLint mPositionHandle;

    GLint mColorHandle;

    GLuint VBO;

    GLuint vertShader;

    GLuint fragShader;

    GLuint VAO;

public:
    RectangleColorFull();

    void initOpenGL() override;

    void resize() override;

    void draw() override;

};
#endif //MY_APPLICATION_COLOR_FULL_H
