package com.leer.lib.net.download;

public class DownloadUtil {

    /**
     * 下载单文件，可以是大文件，该方法不支持断点下载
     *
     * @param url                  文件地址
     * @param destDir              存储文件夹
     * @param fileName             存储文件名
     * @param fileDownLoadObserver 监听回调
     */
//    public static Disposable downloadFile(@NonNull String url, final String destDir,
//                                          final String fileName,
//                                          final FileDownLoadObserver<File> fileDownLoadObserver) {
//        return AppClient.getRetrofit().create(IHoolinkService.class)
//                .downloadFile(url)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .map(new Function<ResponseBody, File>() {
//                    @Override
//                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
//                        return fileDownLoadObserver.saveFile(responseBody, destDir, fileName);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(@NonNull File file) throws Exception {
//                        fileDownLoadObserver.onDownLoadSuccess(file);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        fileDownLoadObserver.onDownLoadFail(throwable);
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        fileDownLoadObserver.onComplete();
//                    }
//                });
//    }
}
