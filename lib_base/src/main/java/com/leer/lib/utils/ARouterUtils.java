package com.leer.lib.utils;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.leer.lib.base.BaseActivity;
import com.leer.lib.base.BaseFragment;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Describe：ARouter帮助类
 * Created by Leer on 2018/11/13.
 */
public class ARouterUtils {

    /**
     * 根据path返回Fragment
     *
     * @param path path
     * @return fragment
     */
    public static BaseFragment getFragment(String path) {
        return (BaseFragment) ARouter.getInstance()
                .build(path)
                .navigation();
    }

    public static BaseFragment goFragmentWitchMap(String path, Map<String, Object> params) {
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        Postcard build = ARouter.getInstance().build(path);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof String) {
                build.withString(key, (String) value);
            } else if (value instanceof Integer) {
                build.withInt(key, (int) value);
            } else if (value instanceof Boolean) {
                build.withBoolean(key, (boolean) value);
            }
        }
        return (BaseFragment) build.navigation();
    }

    /**
     * 根据path返回Activity
     *
     * @param path path
     * @return Activity
     */
    public static BaseActivity getActivity(String path) {
        return (BaseActivity) ARouter.getInstance()
                .build(path)
                .navigation();
    }

    /**
     * 有参数的路由
     *
     * @param path   path
     * @param params 参数
     */
    public static void goActivityWithMap(String path, Map<String, Object> params) {
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();

        Postcard build = ARouter.getInstance().build(path);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof String) {
                build.withString(key, (String) value);
            } else if (value instanceof Integer) {
                build.withInt(key, (int) value);
            } else if (value instanceof Boolean) {
                build.withBoolean(key, (boolean) value);
            }
        }
        build.navigation();
    }

    public static void goActivityWithInt(String path, String key, int value) {
        ARouter.getInstance().build(path).withInt(key, value).navigation();
    }

    public static void goActivityWithString(String path, String key, String value) {
        ARouter.getInstance().build(path).withString(key, value).navigation();
    }

}
