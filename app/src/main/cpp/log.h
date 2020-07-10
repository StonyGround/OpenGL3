//
// Created by cl on 20-5-19.
//

#ifndef MY_APPLICATION_LOG_H
#define MY_APPLICATION_LOG_H


// log标签
#define  TAG    "native_app"
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)

#endif //MY_APPLICATION_LOG_H
