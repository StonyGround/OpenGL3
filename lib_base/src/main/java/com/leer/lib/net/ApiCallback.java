package com.leer.lib.net;

import android.text.TextUtils;

import com.leer.lib.utils.NetworkUtils;
import com.leer.lib.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 作者：Rance on 2016/10/25 15:19
 * 邮箱：rance935@163.com
 * 订阅者，网络错误的回调
 */
public abstract class ApiCallback<M> implements Observer<M> {

    private Disposable mDisposable;

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    protected void onBusinessFailure(String msg) {//平台业务错误
//        ToastUtils.showCustomShort(com.leer.lib.R.mipmap.error_tip, msg);
        ToastUtils.showShort(msg);
    }

    protected boolean intercept() {
        return true;
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(M model) {
//        if (intercept() && model instanceof BaseRsp) {
//            String message = ((BaseRsp) model).getMessage();
//            if (TextUtils.equals("登录超时", message)
//                    || TextUtils.equals("异地登录", message) || TextUtils.equals("登录失效", message) || TextUtils.equals("账号禁用，无法操作！",
//                    message) || TextUtils.equals("login", ((BaseRsp) model).getCode())) {
//                SingleAppDialog.getInstance().setContent(message).show(ActivityUtils.getTopActivity());
//                return;
//            }
//            if (((BaseRsp) model).isStatus()) {
        onSuccess(model);
//            } else {
//                onBusinessFailure(message);
//            }
//        }

    }

    @Override
    public void onComplete() {
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String msg;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 500 || code == 502 || code == 503 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
        } else {
            msg = e.getMessage();
        }
        if (!NetworkUtils.isConnected()) {
            msg = "请检查网络连接";
        }
        if (!TextUtils.isEmpty(msg)) {
            if (msg.equals("timeout")) {
                msg = "请求超时";
            }
            if (msg.equals("connect timed out")) {
                msg = "连接超时";
            }
            if (msg.contains("recvfrom failed")) {
                msg = "服务器中断";
            }
            onFailure(msg);
        }
        onFinish();
    }

}
