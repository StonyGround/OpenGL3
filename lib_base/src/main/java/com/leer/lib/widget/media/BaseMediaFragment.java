package com.leer.lib.widget.media;

import com.leer.lib.base.BaseFragment;

public abstract class BaseMediaFragment extends BaseFragment {
    private ScreenCallback callback;

    public abstract void stop();

    public void finish(int pos) {
        if (callback != null) {
            callback.onFinish(pos);
        }
    }

    public void setScreenCallback(ScreenCallback callback) {
        this.callback = callback;
    }

}
