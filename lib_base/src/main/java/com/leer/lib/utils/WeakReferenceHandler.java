package com.leer.lib.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WeakReferenceHandler extends Handler {
    private WeakReference<Activity> activity;
    private HandleMessageListener handleMessageListener;

    public WeakReferenceHandler(Activity activity, HandleMessageListener handleMessageListener) {
        this.activity = new WeakReference<>(activity);
        this.handleMessageListener = handleMessageListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (activity != null) {
            if (handleMessageListener != null) {
                handleMessageListener.handleMessage(msg);
            }
        }
    }

    public interface HandleMessageListener {
        void handleMessage(Message msg);
    }
}
