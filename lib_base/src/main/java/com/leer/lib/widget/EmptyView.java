package com.leer.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leer.lib.R;

public class EmptyView extends LinearLayout {
    ImageView ivEmpty;
    TextView tvEmpty;
    LinearLayout llEmpty;

    public EmptyView(Context context) {
        this(context, null, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.EmptyView);
//        int resId = mTypedArray.getResourceId(R.styleable.EmptyView_resId, R.mipmap.bg_no_data);
        String text = mTypedArray.getString(R.styleable.EmptyView_text_empty);
        mTypedArray.recycle();

        View inflate = LayoutInflater.from(context).inflate(R.layout.empty_layout, this, true);

//        setImage(resId);
        setText(text);

    }

    public void setText(String text) {
        tvEmpty.setText(text);
    }

    public void setImage(int resId) {
        ivEmpty.setImageResource(resId);
    }
}
