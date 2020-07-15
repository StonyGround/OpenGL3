#include "ggl.h"
#include "utils.h"


AAssetManager *assetManager;
GLuint mProgram;
GLint mPositionHandle;

GLint COORDS_PER_VERTEX;

GLfloat vVertices[] = {0.0f, 0.5f, 0.0f,
                       -0.5f, -0.5f, 0.0f,
                       0.5f, -0.5f, 0.0f};

GLint mColorHandle;

GLfloat c[] = {1.0f, 0.0f, 1.0f, 1.0f};
GLfloat *color;

GLfloat aColor[] = {
        0.0f, 1.0f, 0.0f, 1.0f,
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f
};


GLuint VBO;

GLuint vertShader;

GLuint fragShader;

//加载assets资源
char *loadFileContent(const char *path) {
    char *fileContent = nullptr;
    int fileSize = 0;
    AAsset *asset = AAssetManager_open(assetManager, path, AASSET_MODE_UNKNOWN);
    if (asset == nullptr) {
        LOGE("loadFileContent asset is null, load shader error ");
        return nullptr;
    }
    fileSize = AAsset_getLength(asset);
    fileContent = new char[fileSize];
    AAsset_read(asset, fileContent, fileSize);
    fileContent[fileSize] = '\0';
    AAsset_close(asset);
    LOGD("read file success-----\n%s", fileContent);
    return fileContent;
}

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_initAssetManager(JNIEnv *env, jobject thiz,
                                                                    jobject assets) {
//初始化AssetManager
    assetManager = AAssetManager_fromJava(env, assets);
}

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_initOpenGL(JNIEnv *env, jobject thiz) {

    //生成一个VBO对象
    glGenBuffers(1, &VBO);
    //绑定VBO
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    //把顶点数据复制到VBO中
    glBufferData(GL_ARRAY_BUFFER, sizeof(vVertices), vVertices, GL_STATIC_DRAW);

    char *vertCode = loadFileContent("triangle.vert");
    char *fragCode = loadFileContent("triangle.frag");
    vertShader = loadShader(GL_VERTEX_SHADER, vertCode);
    fragShader = loadShader(GL_FRAGMENT_SHADER, fragCode);

    mProgram = createProgram(vertShader, fragShader);
}

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_onViewportChanged(JNIEnv *env, jobject thiz,
                                                                     jint width, jint height) {
}

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_renderOneFrame(JNIEnv *env, jobject thiz) {

    glClear(GL_COLOR_BUFFER_BIT);

    glUseProgram(mProgram);

    //删除着色器
    glDeleteShader(vertShader);
    glDeleteShader(fragShader);

    //获取变量vPosition的location
    mPositionHandle = glGetAttribLocation(mProgram, "vPosition");

    LOGD("mPositionHandle----%d", mPositionHandle);

//    mMatrixHandler = glGetUniformLocation(mProgram, "vMatrix");
    //指定vMatrix的值
//    glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);

    //启用三角形顶点的句柄
//    glEnableVertexAttribArray(mPositionHandle);
//    //准备三角形的坐标数据
//    glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, GL_FALSE, 0, vVertices);
    glVertexAttribPointer(mPositionHandle, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat),
                          (GLvoid *) nullptr);
    glEnableVertexAttribArray(0);

    //获取片元着色器的vColor成员的句柄
    mColorHandle = glGetUniformLocation(mProgram, "vColor");
    //设置绘制三角形的颜色
    color = c;
    glUniform4fv(mColorHandle, 1, color);
//        glUniform4f(mColorHandle, 1.0f, 0.0f, 1.0f, 1.0f);

//    mColorHandle = glGetAttribLocation(mProgram, "aColor");
//    glEnableVertexAttribArray(mColorHandle);
//    glVertexAttribPointer(mColorHandle, 4, GL_FLOAT, GL_FALSE, 0, aColor);

    glDrawArrays(GL_TRIANGLES, 0, 3);
    //禁止顶点数组的句柄
    glDisableVertexAttribArray(mPositionHandle);
}

#ifdef __cplusplus
}
#endif


