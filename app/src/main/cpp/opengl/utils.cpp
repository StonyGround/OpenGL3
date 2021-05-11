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

    // 删除着色器，它们已经链接到我们的程序中了，已经不再需要了
    glDeleteShader(vsShader);

    return program;
}

char *DecodeBMP(char *bmpFileData, int &width, int &height) {
    if (0x4D42 == *((unsigned short *) bmpFileData)) { // 数据头是否为0x4D42 判断是否是 24位的位图,
        // 读格式头
        int pixelDataOffset = *((int *) (bmpFileData + 10));// 取出 像素数据在内存块的偏移地址
        width = *((int *) (bmpFileData + 18));
        height = *((int *) (bmpFileData + 22));
        char *pixelData = bmpFileData + pixelDataOffset;
        // 位图 像素数据 是 bgr排布的，所以 更换 r b的位置
        for (int i = 0; i < width * height * 3; i += 3) {
            char temp = pixelData[i];
            pixelData[i] = pixelData[i + 2];
            pixelData[i + 2] = temp;

        }
        LOGE("DecodeBMP success ");
        return pixelData;
    }
    LOGE("DecodeBMP error ");
    return nullptr;
}

GLuint createTextureFromBMP(const char *bmpPath) {
    char *bmpFileContent = loadFileContent(bmpPath);
    if (bmpFileContent == nullptr) {
        return 0;
    }
    int bmpWidth = 0, bmpHeight = 0;
    char *pixelData = DecodeBMP(bmpFileContent, bmpWidth, bmpHeight);
    if (pixelData == nullptr) {
        delete[] bmpFileContent;
        LOGE("CreateTextureFromBMP error ");
        return 0;
    }
    GLuint texture = createTexture2D(pixelData, bmpWidth, bmpHeight, GL_RGB);
    delete[] bmpFileContent;
    LOGE("CreateTextureFromBMP success ");
    return texture;
}

GLuint createTexture2D(char *pixelData, int width, int height, GLenum type) {
    GLuint texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);// 表示图像放大时候，使用线性过滤
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);// 表示图像缩小时候，使用线性过滤
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexImage2D(GL_TEXTURE_2D, 0, type, width, height, 0, type, GL_UNSIGNED_BYTE,
                 pixelData);//GL_RGBA
    glBindTexture(GL_TEXTURE_2D, 0);
    return texture;
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
