package com.leer.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.leer.lib.R;
import com.leer.lib.base.mvp.BasePresenter;
import com.leer.lib.base.mvp.IBaseView;
import com.leer.lib.utils.ToastUtils;

/**
 * Describe：所有需要Mvp开发的Activity的基类
 * Created by Leer on 2018/10/15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity
        implements IBaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        if (mPresenter != null) { //创建present
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    //***************************************IBaseView方法实现*************************************
    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onEmpty(String tag) {
        showEmptyView();
    }

    @Override
    public void onEmpty(String tag, int resId) {
        showEmptyView(tag, resId);
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        showEmptyOrErrorView(errorMsg, R.string.app_name);
    }

    @Override
    public void onTip(String msg) {
        ((TextView) ToastUtils.showCustomLong(R.layout.toast_tip)
                .findViewById(R.id.tv_toast_text)).setText(msg);
    }

    @Override
    public void onTip(String msg, int tipIcon) {
        ToastUtils.showCustomShort(tipIcon, msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
    //***************************************IBaseView方法实现*************************************

    public void showLoading(String msg) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(msg)) {
                mLoadingDialog.setTitleText(msg);
            }
            mLoadingDialog.show();
        }
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();


}
