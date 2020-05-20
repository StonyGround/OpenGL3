package com.leer.lib.base.mvp;

import android.content.Context;

/**
 * Describe：所有View基类
 * Created by Leer on 2018/10/17.
 */

public interface IBaseView {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 显示加载框
     */
    void showLoading(String tip);

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 空数据
     *
     * @param tag TAG
     */
    void onEmpty(String tag);

    /**
     * 空数据
     *
     * @param tag TAG
     */
    void onEmpty(String tag, int resId);

    /**
     * 错误数据
     *
     * @param tag      TAG
     * @param errorMsg 错误信息
     */
    void onError(Object tag, String errorMsg);

    /**
     * 吐司提示
     *
     * @param msg 提示内容
     */
    void onTip(String msg);

    /**
     * 吐司提示
     */
    void onTip(String msg, int tipIcon);

    /**
     * 上下文
     *
     * @return context
     */
    Context getContext();
}
