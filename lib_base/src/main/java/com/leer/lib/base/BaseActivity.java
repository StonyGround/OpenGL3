package com.leer.lib.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.leer.lib.R;
import com.leer.lib.event.Event;
import com.leer.lib.utils.EventBusUtils;
import com.leer.lib.utils.StateWindowUtil;
import com.leer.lib.widget.ActionBar;
import com.leer.lib.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Describe：所有Activity的基类
 * Created by Leer on 2018/10/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    private ViewStub mEmptyView;

    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected LoadingDialog mLoadingDialog;

    protected ActionBar mActionbar;

    protected LinearLayout ll_base;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mActionbar = findViewById(R.id.actionbar);
        ll_base = findViewById(R.id.ll_base);
        mActionbar.setVisibility(getMyActionBar() ? View.VISIBLE : View.GONE);
        findViewById(R.id.view_line).setVisibility(getMyActionBar() ? View.VISIBLE : View.GONE);
        ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().
                inflate(getLayoutId(), null));
        //初始化ButterKnife

        if (regEvent()) {
            EventBusUtils.register(this);
        }

        //沉浸式状态栏
        initImmersionBar(R.color.white);
        //设置状态栏文字颜色
        StateWindowUtil.statusBarLightMode(this, true);

        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setCancelable(false);
    }

    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean getMyActionBar() {
        return true;
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    //***************************************空页面方法*************************************
    protected void showEmptyView(String text) {
//        showEmptyOrErrorView(text, R.mipmap.bg_no_data);
    }

    protected void showEmptyView(String text, int resId) {
        showEmptyOrErrorView(text, resId);
    }

    protected void showEmptyView() {
        showEmptyView(getString(R.string.str_no_data));
    }

    protected void showErrorView(String text) {
//        showEmptyOrErrorView(text, R.mipmap.bg_no_net);
    }

    protected void showErrorView() {
        showErrorView(getString(R.string.str_error_data));
    }

    public void showEmptyOrErrorView(String text, int img) {
        if (mEmptyView == null) {
            mEmptyView = findViewById(R.id.vs_empty);
        }
        mEmptyView.setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.iv_empty)).setImageResource(img);
        ((TextView) findViewById(R.id.tv_empty)).setText(text);
        findViewById(R.id.ll_empty).setOnClickListener(view -> onPageClick());
    }

    protected void hideEmptyView() {
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {
    }
    //***************************************空页面方法*********************************

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果有未授权权限则跳转设置页面
        if (!requestPermissionsResult(grantResults)) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 判断授权结果
     */
    private boolean requestPermissionsResult(int[] grantResults) {
        for (int code : grantResults) {
            if (code == -1) return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
