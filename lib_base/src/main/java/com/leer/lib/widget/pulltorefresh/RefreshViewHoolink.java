package com.leer.lib.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.leer.lib.R;
import com.leer.lib.widget.LVCircularRingHoolink;

/**
 * 刷新控件
 */
public class RefreshViewHoolink extends FrameLayout implements IRefreshView {

    //    private ProgressBar lvCircularRing;
    private LVCircularRingHoolink lvCircularRing;
    private boolean refresh = false;//是否是刷新

    public RefreshViewHoolink(Context context) {
        this(context, null);
    }

    public RefreshViewHoolink(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshViewHoolink(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void setType(boolean refresh) {
        this.refresh = refresh;
    }

    private void init(Context context) {
        View childView = View.inflate(context, R.layout.layout_refresh_view_hoolink, null);
        addView(childView);
        lvCircularRing = childView.findViewById(R.id.header_progress);
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {
        float s = progress / all;
        if (s >= 1f) {
            lvCircularRing.setSweepAngle(270);
        } else {
            lvCircularRing.setSweepAngle(s * 270);
        }
    }

    @Override
    public void finishing(float progress, float all) {
        lvCircularRing.stopAnim();
    }

    @Override
    public void loading() {
        lvCircularRing.startAnim();
    }

    @Override
    public void normal() {
        lvCircularRing.stopAnim();
    }

    @Override
    public View getView() {
        return this;
    }
}
