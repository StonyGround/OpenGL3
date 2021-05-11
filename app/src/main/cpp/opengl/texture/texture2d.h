//
// Created by cl on 2020/7/17.
//
#include "../base_gl.h"
#include "../ggl.h"
#include "../utils.h"

#ifndef MY_APPLICATION_TEXTURE2D_H
#define MY_APPLICATION_TEXTURE2D_H

class Texture2D : public IOpenGL {
private:

    GLuint mProgram;

    GLuint VAO;

    GLuint texture;
public:
    Texture2D();

    void initOpenGL() override;

    void resize() override;

    void draw() override;


};

#endif //MY_APPLICATION_TEXTURE2D_H
