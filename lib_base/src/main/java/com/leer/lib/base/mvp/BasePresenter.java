package com.leer.lib.base.mvp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.leer.lib.net.AppClient;
import com.leer.lib.net.InvoiceService;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe：Presenter基类
 * Created by Leer on 2018/10/17.
 */
@SuppressWarnings("unchecked")
public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> {

    private M mModule;
    private V mProxyView;
    private WeakReference<V> mWeakReference;

    protected InvoiceService mHooService;

    /**
     * 绑定View
     */
    public void attachView(V view) {
        mWeakReference = new WeakReference<>(view);
        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new MvpViewHandler(mWeakReference.get()));
        if (this.mModule == null) {
            this.mModule = createModule();
        }
        mHooService = AppClient.getRetrofit().create(InvoiceService.class);
    }

    /**
     * 解绑View
     */
    public void detachView() {
        this.mModule = null;
        if (isViewAttached()) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    /**
     * 初始化方法
     */
    public abstract void start();

    protected V getView() {
        return mProxyView;
    }

    protected M getModule() {
        return mModule;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    protected void showLoading() {
        getView().showLoading();
    }

    protected void showLoading(String tip) {
        getView().showLoading(tip);
    }

    protected void dismissLoading() {
        getView().dismissLoading();
    }

    //子线程工作相关代码（网络、数据库操作等）
    protected void addObserver(Observable observable, @NonNull Observer apiCallback) {
        observable.subscribeOn(Schedulers.io())
                .subscribe(apiCallback);
    }

    //子线程工作相关代码（网络、数据库操作等）

    /**
     * 通过该方法创建Module
     */
    protected abstract M createModule();

    /**
     * 是否与View建立连接
     */
    protected boolean isViewAttached() {
        return mWeakReference != null && mWeakReference.get() != null;
    }

    /**
     * View代理类  防止 页面关闭P异步操作调用V 方法 空指针问题
     */
    private class MvpViewHandler implements InvocationHandler {

        private IBaseView mvpView;

        MvpViewHandler(IBaseView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mvpView, args);
            }//P层不需要关注V层的返回值
            return null;
        }
    }

}