package com.leer.lib.constants;

import android.os.Environment;

/**
 * Describe：常量存放
 * Created by Leer on 2017/10/9.
 */
public class Constants {

    public static final int KB_SIZE = 1024;
    public static final int MB_SIZE = 1024 * 1024;

    /**
     * 服务器类型
     * SERVER_DEVELOP       开发环境
     * SERVER_TEST          测试环境
     * SERVER_PRODUCTION    生产环境
     */
    public static final int SERVER_TYPE = ServerType.SERVER_DEVELOP;

    //极光id
    public static final String REGISTRATION_ID = "registration_id";

    //场景id
    public static final String SCENE_ID = "scene_id";
    public static final String USER_TYPE = "user_type";

    public static volatile String mToken = "";
    public static volatile int mPID;
//    public static boolean mIsReadOnly;

    /**
     * 服务器类型
     */
    public class ServerType {
        public static final int SERVER_DEVELOP = 0;//开发环境
        public static final int SERVER_TEST = 1;//测试环境
        public static final int SERVER_PRODUCTION = 2;//生产环境
    }

    /**
     * 默认日期格式
     */
    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DEFAULT = DATE_FORMAT_SLASH + " HH:mm:ss";

    /////////////////////////////////////////////
    /**
     * 分页加载最大条数
     */
    public static final int PAGE_MAX_NUM = 25;

    public static final int CHECK_USER_FAILED = -1;
    public static final int CHECK_USER_SUCCESS = 0;
    public static final int CHECK_USER_NO_PERMISSION = 1;

    public static final int TYPE_LEVEL_0 = 0;//父级
    public static final int TYPE_LEVEL_1 = 1;//子级

    public static final int MAIN_TYPE_WORKORDER = 1;
    public static final int MAIN_TYPE_MY_NEED_TODO_LIST = 2;

    public static final int VOICE_CONTENT_LEFT = 0;
    public static final int VOICE_CONTENT_RIGHT = 1;
    public static final int VOICE_CONTENT_LIST = 2;


    private final static String MAIN_FILE_PATH = Environment.getExternalStorageDirectory() +
            "/Android/data/";

    public final static String APP_FILE_PATH = MAIN_FILE_PATH + "com.hoolink.iot/cache/";

    public final static String DOWNLOAD = Environment.getExternalStorageDirectory()+"/Download/";

    public static final String DCIM_CAMERA =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera/";

    //监控截屏文件
    public final static String CAMERA_CAPTURE_PATH = APP_FILE_PATH + "capture.jpg";
    //监控录像文件
    public final static String CAMERA_RECORD_PATH = APP_FILE_PATH + "record.mp4";
    //监控录像文件第一帧缩略图
    public final static String CAMERA_RECORD_THUMB_PATH = APP_FILE_PATH + "thumb.jpg";

    public final static String APK_FILE_NAME = "new.apk";

    //录音文件缓存路径
    public final static String VOICE_FILE_PATH = MAIN_FILE_PATH + "com.hoolink.iot/cache/voice.pcm";

    public static final String OPERATIONS = "OPERATIONS";
    public static final String MY_TODO = "MY_TODO";
    public static final String WORK_TABLE = "WORK_TABLE";

    //温度
    public static final int MONITOR_TEMPERATURE = 5;
    //湿度
    public static final int MONITOR_HIMIDITY = 6;
    //气压
    public static final int MONITOR_AIR_PRESSURE = 7;
    //辐射
    public static final int MONITOR_RADIATION = 8;
    //风向
    public static final int MONITOR_WIND_DIRECTION = 9;
    //风速
    public static final int MONITOR_WIND_SPEED = 10;
    //PM2.5
    public static final int MONITOR_PM25 = 11;
    //PM10
    public static final int MONITOR_PM10 = 12;
    //紫外线
    public static final int MONITOR_ULTRAVIOLET = 13;
    //噪声
    public static final int MONITOR_NOISE = 14;
    //雨量
    public static final int MONITOR_RAINFALL = 15;
    //SO2
    public static final int MONITOR_SO2 = 16;
    //H2S
    public static final int MONITOR_H2S = 17;
    //No
    public static final int MONITOR_NO = 18;
    //NO2
    public static final int MONITOR_NO2 = 19;
    //O3
    public static final int MONITOR_O3 = 20;
    //CO
    public static final int MONITOR_CO = 21;
    //光照度
    public static final int MONITOR_ILLUMINATION = 29;
}
