#include <jni.h>
#include <string>
#include <android/log.h>
#include "log.h"

#define RESULT_OK      0
#define RESULT_ERROR  -1

const char *path;

extern "C"
JNIEXPORT int JNICALL
Java_com_kiddo_myapplication_MainActivity_createFile(JNIEnv *env, jobject thiz,
                                                     jstring filepath) {
    path = (*env).GetStringUTFChars(filepath, 0);

    int result = RESULT_ERROR;
    FILE *file = NULL;
    file = fopen(path, "w+");
    if (file == NULL) {
        result = RESULT_ERROR;
        return result;
    }
    char *test = "aaa";
    LOGD("%s", test);
    LOGD("path length=%d", strlen(path));
    for (int i = 0; i < strlen(path); i++) {
        LOGD("aaaa %s", test);
    }

    fwrite(path, strlen(path), 1, file);
    result = RESULT_OK;
    fclose(file);

    (*env).ReleaseStringUTFChars(filepath, path);
    return result;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_kiddo_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    FILE *file = fopen(path, "r");
    char *content = nullptr;
    fread(content, strlen(path), 1, file);
    LOGD("content: %s", content);
    return env->NewStringUTF(content);
}

