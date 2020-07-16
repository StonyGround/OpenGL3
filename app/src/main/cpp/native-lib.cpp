#include "ggl.h"
#include "utils.h"
#include "shape/shape_manager.h"
#include "shape/triangle.h"


AAssetManager *assetManager;

Shape *shape;

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
    shape = ShapeManager::create(S_RECTANGLE);
    shape->initOpenGL();
}

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_onViewportChanged(JNIEnv *env, jobject thiz,
                                                                     jint width, jint height) {
    shape->resize();
}

JNIEXPORT void JNICALL
Java_com_kiddo_myapplication_Native_00024Companion_renderFrame(JNIEnv *env, jobject thiz) {
    shape->draw();
}

#ifdef __cplusplus
}
#endif


