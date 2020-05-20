package com.leer.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leer.lib.R;
import com.leer.lib.widget.LVCircularRing;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 等待对话框
 * Created by Leer on 2016/3/14.
 */
public class LoadingDialog {

    //    private LVCircularRing mLoadingView;
    private AVLoadingIndicatorView mLoadingView;
    private Dialog mLoadingDialog;
    private Context context;
    private String msg = "加载中···";
    private boolean cancelable = true;
    private boolean isShow;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    /**
     * 设置提示信息
     */
    public LoadingDialog setTitleText(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 返回键是否可用
     */
    public LoadingDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public void show() {
        View view = View.inflate(context, R.layout.dialog_loading, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.avl_loading);
        // 页面中显示文本
        TextView loadingText = view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.LoadingDialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        mLoadingView.show();
        isShow = true;
    }

    public void dismiss() {
        if (mLoadingDialog != null && isShow) {
            mLoadingView.hide();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
            msg = "加载中···";
            isShow = false;
        }
    }

    public boolean isShowing() {
        return isShow;
    }
}
