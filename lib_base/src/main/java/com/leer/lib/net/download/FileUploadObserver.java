package com.leer.lib.net.download;

import org.json.JSONException;

import java.io.IOException;

/**
 * 上传文件的回调
 */

public abstract class FileUploadObserver<T> {


    //    @Override
//    public void onNext(T t) {
//        onUpLoadSuccess(t);
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        onUpLoadFail(e);
//    }
//
    //可以重写，具体可由子类实现
    public void onComplete() {

    }

    public void onProgressChange(int partNum, long bytesWritten, long contentLength) {

        onProgress((int) (bytesWritten * 100 / contentLength), partNum);
    }

    //上传成功的回调
    public abstract void onUpLoadSuccess(T t) throws IOException, JSONException;

    //上传你失败回调
    public abstract void onUpLoadFail(Throwable e);

    //上传进度回调
    public abstract void onProgress(int progress, int partNum);

}
