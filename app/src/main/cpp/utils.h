//
// Created by cl on 2020/7/14.
//
#include "ggl.h"

#ifndef MY_APPLICATION_UTILS_H
#define MY_APPLICATION_UTILS_H


GLuint loadShader(GLenum shaderType, const char *shaderCode);

GLuint createProgram(GLuint vsShader, GLuint fsShader);

GLuint createTextureFromBMP(const char *bmpPath);

GLuint createTexture2D(unsigned char *pixelData, int Width, int height, GLenum type);

unsigned char *loadFileContent(const char *path, int &fileSize);

char *readFile(const char *path);

#endif //MY_APPLICATION_UTILS_H