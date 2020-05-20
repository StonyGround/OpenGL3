package com.leer.lib.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class KillSelfService extends Service {
    private Handler handler;

    public KillSelfService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        long stopDelayed = intent.getLongExtra("Delayed", 2000);
        handler.postDelayed(() -> {
            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
            LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(LaunchIntent);
            KillSelfService.this.stopSelf();
        }, stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
