//
// Created by cl on 2020/7/13.
//

#include "utils.h"

//加载着色器
GLuint loadShader(GLenum shaderType, const char *shaderCode) {
    GLuint shader = glCreateShader(shaderType);
    glShaderSource(shader, 1, &shaderCode, nullptr);
    glCompileShader(shader);

    //编译错误输出
    GLint success;
    GLchar infoLog[512];
    glGetShaderiv(shader, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(shader, 512, nullptr, infoLog);
        LOGE("ERROR::SHADER::COMPILATION_FAILED\n%s", infoLog);
    }
    return shader;
}

//创建program
GLuint createProgram(GLuint vsShader, GLuint fsShader) {
    GLuint program = glCreateProgram();
    glAttachShader(program, vsShader);
    glAttachShader(program, fsShader);
    glLinkProgram(program);

    //编译错误输出
    GLint success;
    GLchar infoLog[512];
    glGetProgramiv(program, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(program, 512, nullptr, infoLog);
        LOGE("ERROR::SHADER::COMPILATION_FAILED\n%s", infoLog);
    }
    return program;
}

GLuint createTextureFromBMP(const char *bmpPath) {
    return 0;
}

GLuint createTexture2D(unsigned char *pixelData, int Width, int height, GLenum type) {
    return 0;
}

char *readFile(const char *path) {
    FILE *pFile = fopen(path, "r");
    if (pFile == nullptr) {
        return nullptr;
    }
    fseek(pFile, 0, SEEK_END);
    long length = ftell(pFile);
    LOGD("%s%ld", "file length:", length);
    char *data = static_cast<char *>(malloc(length * sizeof(char)));
    rewind(pFile);
    int result = fread(data, 0, length, pFile);
    if (result != length) {
        return nullptr;
    }
    return data;
}
