package com.leer.lib.base;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.leer.lib.R;
import com.leer.lib.base.mvp.BasePresenter;
import com.leer.lib.base.mvp.IBaseView;
import com.leer.lib.utils.ToastUtils;

/**
 * Describe：所有需要Mvp开发的Fragment的基类
 * Created by Leer on 2018/10/17.
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment
        implements IBaseView {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) { //创建present
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    //***************************************IBaseView方法实现*************************************
    @Override
    public void showLoading() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
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
//        showEmptyOrErrorView(errorMsg, R.mipmap.error_tip);
    }

    @Override
    public void showLoading(String msg) {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(msg)) {
                mLoadingDialog.setTitleText(msg);
            }
            mLoadingDialog.show();
        }
    }

    @Override
    public void onTip(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onTip(String msg, int tipIcon) {
        ToastUtils.showCustomShort(tipIcon, msg);
    }

    //***************************************IBaseView方法实现*************************************

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();
}
