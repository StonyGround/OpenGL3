package com.leer.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.leer.lib.R;

public class ObserverEditText extends AppCompatEditText {

    private int minLen;

    private boolean joinObserver;

    public ObserverEditText(Context context) {
        this(context, null);
    }

    public ObserverEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ObserverEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public boolean canPress() {
        return getText().length() >= minLen;
    }

    public boolean getJoinObserver() {
        return joinObserver;
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ObserverEditText);
        minLen = a.getInt(R.styleable.ObserverEditText_minWordNumber, 1);

        joinObserver = a.getBoolean(R.styleable.ObserverEditText_join, false);
        a.recycle();
    }

}
