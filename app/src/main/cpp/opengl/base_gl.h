//
// Created by cl on 2020/7/15.
//

#ifndef MY_APPLICATION_BASE_GL_H
#define MY_APPLICATION_BASE_GL_H


class IOpenGL {
public:
    virtual void initOpenGL();

    virtual void resize();

    virtual void draw();

};


#endif //MY_APPLICATION_BASE_GL_H
