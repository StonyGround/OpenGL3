package com.leer.lib.net;

import io.reactivex.Observable;
import okhttp3.Request;
import retrofit2.http.GET;

public interface InvoiceService {
    //    @GET("APPLogin.jsp")
//    Observable<Object> login(@Query("user") String userId, @Query("pwd") String type);
    @GET
    Observable<Object> login(Request request);
}
