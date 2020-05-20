package com.leer.lib.utils;

import android.os.CountDownTimer;

/**
 * 短信倒计时
 *
 * @author kiddo
 */
public class CountTimerUtils {
    private static final long COUNT_TIME = 60 * 1000;

    private CountDownTimer countDownTimer;
    private CountTimerListener countTimerListener;
    private static CountTimerUtils countTimerUtils;
    private long currentTime = COUNT_TIME;

    public static CountTimerUtils getInstance() {
        if (countTimerUtils == null) {
            synchronized (CountTimerUtils.class) {
                if (countTimerUtils == null) {
                    countTimerUtils = new CountTimerUtils();
                }
            }
        }
        return countTimerUtils;
    }

    private CountTimerUtils() {
        countDownTimer = new CountDownTimer(COUNT_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTime = millisUntilFinished;
                if (countTimerListener != null) {
                    countTimerListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                currentTime = COUNT_TIME;
                if (countTimerListener != null) {
                    countTimerListener.onFinish();
                }
            }
        };
    }

    public void start() {
        if (currentTime == COUNT_TIME) {
            countDownTimer.start();
        }
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            currentTime = COUNT_TIME;
        }
    }

    public void unBind() {
        if (countTimerListener != null) {
            countTimerListener = null;
        }
    }

    public void setCountTimerListener(CountTimerListener countTimerListener) {
        this.countTimerListener = countTimerListener;
    }

    public interface CountTimerListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
