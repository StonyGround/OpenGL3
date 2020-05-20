package com.leer.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.leer.lib.R;
import com.leer.lib.utils.ConvertUtils;
import com.leer.lib.utils.SizeUtils;

/**
 * Describe：自定义的ActionBar
 * Created by Leer on 2017/8/22.
 */
public class ActionBar extends RelativeLayout {

    private LinearLayout mLlActionbar;
    private LinearLayout mLlLeft;
    private LinearLayout mLlCenter;
    private LinearLayout mLlRight;

    /**
     * 总布局
     */
    private View mRootView;

    /**
     * 字体颜色
     */
    private int leftTextColor = R.color.title_bar_text_color;
    private int centerTextColor = R.color.title_bar_text_color;
    private int rightTextColor = R.color.title_bar_text_color;

    /**
     * 字体大小
     */
    private int lefTextSize = 16;
    private int centerTextSize = 18;
    private int rightTextSize = 16;

    public ActionBar(Context context) {
        this(context, null);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public void setLefTextSize(int lefTextSize) {
        this.lefTextSize = lefTextSize;
    }

    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public void setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
    }

    /**
     * 获取跟布局
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 初始化界面
     */
    private void initView(Context context) {
        mRootView = View.inflate(context, R.layout.action_bar, this);
        mLlActionbar = findViewById(R.id.ll_actionbar);
        mLlLeft = findViewById(R.id.ll_actionbar_left);
        mLlCenter = findViewById(R.id.ll_actionbar_centre);
        mLlRight = findViewById(R.id.ll_actionbar_right);
//        showBackImgAndTip(true);
        showBackImg(true);
    }

    /**
     * 设置左边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setLeftIcon(int res, OnClickListener l) {
        mLlLeft.setVisibility(VISIBLE);
        mLlLeft.removeAllViews();
        ImageView ivLeft = new ImageView(getContext());
        ivLeft.setImageResource(res);
        mLlLeft.addView(ivLeft);
        mLlLeft.setOnClickListener(l);
    }

    /**
     * 设置左边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setLeftIcon2(int res, int res2, OnClickListener l) {
        mLlLeft.setVisibility(VISIBLE);
        mLlLeft.removeAllViews();
        ImageView ivLeft = new ImageView(getContext());
        ivLeft.setImageResource(res);
        mLlLeft.addView(ivLeft);
        ImageView ivLeft2 = new ImageView(getContext());
        ivLeft2.setPadding(SizeUtils.dp2px(13), 0, 0, 0);
        ivLeft2.setImageResource(res2);
        mLlLeft.addView(ivLeft2);
        ivLeft.setOnClickListener(l);
        ivLeft2.setOnClickListener(l);
    }

    /**
     * 设置左边图标和文字提示
     *
     * @param res      图片资源
     * @param listener 监听器
     */
    public void setLeftIconAndTip(int res, int strRes, OnClickListener listener) {
        mLlLeft.setVisibility(VISIBLE);
        mLlLeft.removeAllViews();
        ImageView ivLeft = new ImageView(getContext());
        ivLeft.setImageResource(res);
        TextView left = getTextView();
        left.setText(getResources().getString(strRes));
        left.setTextColor(getResources().getColor(leftTextColor));
        left.setTextSize(lefTextSize);
        left.setPadding(20, 0, 0, 0);
        mLlLeft.addView(ivLeft);
        mLlLeft.addView(left);
        mLlLeft.setOnClickListener(listener);
    }

    public void setLeftTip(int strRes) {
//        setLeftIconAndTip(R.mipmap.ic_back, strRes, v -> {
//            Context ctx = ActionBar.this.getContext();
//            if (ctx instanceof Activity) {
//                ((Activity) ctx).onBackPressed();
//            }
//        });
    }

    /**
     * 设置中间图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setCenterIcon(int res, OnClickListener l) {
        mLlCenter.setVisibility(VISIBLE);
        mLlCenter.removeAllViews();
        ImageView center = new ImageView(getContext());
        center.setImageResource(res);
        mLlCenter.addView(center);
        mLlCenter.setOnClickListener(l);
    }

    public void setRightsVisibility(boolean visible) {
        mLlRight.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置右边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setRightIcon(int res, OnClickListener l) {
        mLlRight.setVisibility(VISIBLE);
        mLlRight.removeAllViews();
        ImageView right = new ImageView(getContext());
        right.setImageResource(res);
        mLlRight.addView(right);
        if (l != null) {
            mLlRight.setOnClickListener(l);
        }
    }

    public void setLeftVisibility(boolean visible) {
        mLlLeft.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setLeftText(CharSequence text, OnClickListener l) {
        mLlLeft.setVisibility(VISIBLE);
        mLlLeft.removeAllViews();
        TextView left = getTextView();
        left.setText(text);
        left.setTextColor(getResources().getColor(leftTextColor));
        left.setTextSize(lefTextSize);
        mLlLeft.addView(left);
        if (l != null) {
            mLlLeft.setOnClickListener(l);
        }
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     */
    public void setLeftText(CharSequence text) {
        setLeftText(text, null);
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setCenterText(CharSequence text, OnClickListener l) {
        mLlCenter.setVisibility(VISIBLE);
        mLlCenter.removeAllViews();
        TextView center = getTextView();
        center.setTextSize(centerTextSize);
        center.setText(text);
        center.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        center.setEllipsize(TextUtils.TruncateAt.END);
        center.setMaxLines(1);
        center.setTextColor(getResources().getColor(centerTextColor));
        mLlCenter.addView(center);
        if (l != null) {
            mLlCenter.setOnClickListener(l);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setCenterText(CharSequence text, boolean isBold, OnClickListener l) {
        mLlCenter.setVisibility(VISIBLE);
        mLlCenter.removeAllViews();
        TextView center = getTextView();
        center.setTextSize(centerTextSize);
        center.setText(text);
        center.setEllipsize(TextUtils.TruncateAt.END);
        center.setMaxLines(1);
        if (isBold) {
            center.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        center.setTextColor(getResources().getColor(centerTextColor));
        mLlCenter.addView(center);
        if (l != null) {
            mLlCenter.setOnClickListener(l);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     */
    public void setCenterText(CharSequence text) {
        setCenterText(text, null);
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     */
    public void setCenterText(CharSequence text, boolean isBold) {
        setCenterText(text, isBold, null);
    }

    /**
     * 设置右边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setRightText(CharSequence text, OnClickListener l) {
        mLlRight.setVisibility(VISIBLE);
        mLlRight.removeAllViews();
        TextView tvRight = getTextView();
        tvRight.setText(text);
        tvRight.setTextSize(rightTextSize);
        tvRight.setTextColor(ContextCompat.getColor(getContext(), rightTextColor));
        mLlRight.addView(tvRight);
        if (l != null) {
            mLlRight.setOnClickListener(l);
        }
    }

    /**
     * 设置右边文字
     *
     * @param text 文字
     */
    public void setRightText(CharSequence text) {
        setRightText(text, null);
    }

    /**
     * 得到右边的布局
     *
     * @return View
     */
    public LinearLayout getRightView() {
        return mLlRight;
    }

    /**
     * 得到中间的布局
     *
     * @return View
     */
    public LinearLayout getCenterView() {
        return mLlCenter;
    }

    /**
     * 得到左边的布局
     *
     * @return View
     */
    public LinearLayout getLeftView() {
        return mLlLeft;
    }

    /**
     * 设置左边布局
     *
     * @param v v
     */
    public void setLeftView(View v) {
        mLlLeft.setVisibility(VISIBLE);
        mLlLeft.removeAllViews();
        mLlLeft.addView(v);
    }

    /**
     * 设置中间布局
     *
     * @param v v
     */
    public void setCenterView(View v) {
        mLlCenter.setVisibility(VISIBLE);
        mLlCenter.removeAllViews();
        mLlCenter.addView(v);
    }

    /**
     * 设置右边的view
     *
     * @param v v
     */
    public void setRightView(View v) {
        setRightView(v, null);
    }

    public void setRightView(View v, OnClickListener listener) {
        mLlRight.removeAllViews();
        mLlRight.addView(v);
        if (listener != null) {
            v.setOnClickListener(listener);
        }
    }

    /**
     * 显示默认的左边的按钮
     */
    public void showBackImg(boolean show) {
        if (show) {
//            setLeftIcon(R.mipmap.ic_back, v -> {
//                Context ctx = ActionBar.this.getContext();
//                if (ctx instanceof Activity) {
//                    ((Activity) ctx).onBackPressed();
//                }
//            });
        } else {
            mLlLeft.setVisibility(INVISIBLE);
        }
    }

    /**
     * 显示默认的左边的按钮
     */
    public void showBackImgAndTip(boolean show) {
        if (show) {
//            setLeftIconAndTip(R.mipmap.ic_back, R.string.str_back, v -> {
//                Context ctx = ActionBar.this.getContext();
//                if (ctx instanceof Activity) {
//                    ((Activity) ctx).onBackPressed();
//                }
//            });
        } else {
            mLlLeft.setVisibility(INVISIBLE);
        }
    }

    /**
     * ActionBar整个替换
     *
     * @param v v
     */
    public void setActionBarView(View v) {
        mLlActionbar.removeAllViews();
        mLlActionbar.addView(v);
    }

    /**
     * 获取textView
     */
    public TextView getTextView() {
        TextView tv = new TextView(getContext());
        tv.setTextSize(centerTextSize);
        tv.setTextColor(ContextCompat.getColor(getContext(), centerTextColor));
        return tv;
    }

    /**
     * 获取imageView
     */
    public ImageView getImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(new LayoutParams(ConvertUtils.dp2px(25),
                ConvertUtils.dp2px(25)));
        return iv;
    }
}
