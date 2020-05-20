package com.leer.lib.net;

import com.leer.lib.BuildConfig;
import com.leer.lib.utils.AppUtils;
import com.leer.lib.utils.NetworkUtils;
import com.leer.lib.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 作者：luoyin on 2017/06/19 13:26
 * 邮箱：2540757573@qq.com
 */
public class AppClient {
    private volatile static Retrofit mTradeRetrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofit() {
        if (mTradeRetrofit == null) {
            mTradeRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
//                    .addConverterFactory(JsonConverterFactory.create())
                    .addConverterFactory(JsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mTradeRetrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (AppUtils.isAppDebug()) {
//                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            builder.addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
//                        .addHeader("Content-Type", "text/json;charset=UTF-8")
//                        .addHeader("Content-Type", "text/javascript;charset=UTF-8")
//                        .addHeader("X-Mobile", "android")
//                        .addHeader("pid", String.valueOf(269))
                        .build();
                return chain.proceed(request);
            });
            builder.connectTimeout(10, TimeUnit.SECONDS);//10
            builder.readTimeout(20, TimeUnit.SECONDS);//20
            builder.writeTimeout(20, TimeUnit.SECONDS);//20
            //错误重连
            builder.retryOnConnectionFailure(true);
            okHttpClient = builder.build();

            File httpCacheDirectory = new File(PathUtils.getExternalAppDataPath(),
                    "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
//            builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-xcached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    /**
     * 单上传文件的封装
     *
     * @param params             参数
     * @param part               需要上传的文件
     * @param fileUploadObserver 上传监听回调
     */
//    public static Disposable upLoadFile(Map<String, RequestBody> params, MultipartBody.Part part,
//                                        final FileUploadObserver<ResponseBody>
//                                        fileUploadObserver) {
//        return getRetrofit().create(IHoolinkService.class)
//                .uploadEdm(params, part)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
//                        fileUploadObserver.onUpLoadSuccess(responseBody);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        fileUploadObserver.onUpLoadFail(throwable);
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        fileUploadObserver.onComplete();
//                    }
//                });
//    }

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
//        return getRetrofit().create(IEDMService.class)
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
