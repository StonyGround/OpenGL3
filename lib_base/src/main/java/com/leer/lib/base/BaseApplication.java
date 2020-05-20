package com.leer.lib.base;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leer.lib.BuildConfig;
import com.leer.lib.utils.ActivityUtils;
import com.leer.lib.utils.ToastUtils;
import com.leer.lib.utils.Utils;

/**
 * Describe：基础Application所有需要模块化开发的module都需要继承自BaseApplication
 * Created by Leer on 2018/10/12.
 */
public class BaseApplication extends Application {

    //全局唯一的context
    private static BaseApplication mApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApp = this;
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
        Utils.init(this);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApp);// 尽可能早，推荐在Application中初始化
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 获取全局唯一上下文
     */
    public static BaseApplication getApp() {
        return mApp;
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        ActivityUtils.finishAllActivities();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}