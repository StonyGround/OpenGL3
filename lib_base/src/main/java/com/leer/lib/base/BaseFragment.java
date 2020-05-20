package com.leer.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leer.lib.R;
import com.leer.lib.event.Event;
import com.leer.lib.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Describe：所有Fragment的基类
 * Created by Leer on 2018/10/17.
 */
public abstract class BaseFragment extends Fragment {

    private ViewStub mEmptyView;
    private View mRootView;

    protected Context mContext;
    protected LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mLoadingDialog = new LoadingDialog(mContext);
        if (regEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_base, container, false);
        ((ViewGroup) mRootView.findViewById(R.id.fl_content)).addView(getLayoutInflater()
                .inflate(getLayoutId(), null));

        return mRootView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (regEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventBus(Event event) {
    }

    /**
     * 需要接收事件 重新该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void setCancelable(boolean cancelable) {
        mLoadingDialog.setCancelable(cancelable);
    }

    //***************************************空页面方法*************************************
    protected void showEmptyView() {
//        showEmptyOrErrorView(getString(R.string.str_no_data), R.mipmap.bg_no_data);
    }

    protected void showEmptyView(String tag,int resId) {
        showEmptyOrErrorView(tag, resId);
    }

    protected void showErrorView() {
//        showEmptyOrErrorView(getString(R.string.str_error_data), R.mipmap.bg_no_net);
    }

    protected void showEmptyOrErrorView(String text, int img) {
        if (mEmptyView == null) {
            mEmptyView = mRootView.findViewById(R.id.vs_empty);
        }
        mEmptyView.setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.iv_empty).setBackgroundResource(img);
        ((TextView) mRootView.findViewById(R.id.tv_empty)).setText(text);
        mRootView.findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPageClick();
            }
        });
    }

    protected void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {
    }
    //***************************************空页面方法*********************************

}
