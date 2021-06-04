package com.agridata.grasstong.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import java.io.File;
import java.util.List;

/**
 * 应用程序工具类
 * <p>
 * Created by Guoxin on 2018/3/27.
 */
public class AppUtil {

    /**
     * App存储目录
     */
    public static final String PATH_APP = "GrassTong";    // 农安星根目录
    public static final String PATH_APP_LOG = PATH_APP + "/log";    // 日志目录
    public static final String PATH_APP_RESOURCE = PATH_APP + "/.resource";  // 资源目录，隐藏目录
    public static final String PATH_APP_IMAGE = PATH_APP + "/image";   // 图片目录
    public static final String PATH_APP_FILE = PATH_APP + "/file";   // 文件目录
    public static final String PATH_APP_THUMB = PATH_APP + "/.thumb";  // 缩略图目录，隐藏目录
    public static final String ABSOLUTE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();    // 绝对根路径
    public static final String ABSOLUTE_PATH_APP_IMAGE = ABSOLUTE_PATH + "/" + PATH_APP_IMAGE;   // 绝对图片目录
    public static final String ABSOLUTE_PATH_APP_FILE = ABSOLUTE_PATH + "/" + PATH_APP_FILE;   // 绝对文件目录
    public static final String ABSOLUTE_PATH_APP_THUMB = ABSOLUTE_PATH + "/" + PATH_APP_THUMB;  // 绝对缩略图目录，隐藏目录

    private static Boolean mIsDebug = true; // 是否是debug模式



    /**
     * 是否是debug模式
     *
     * @return
     */
    public static boolean isDebug() {
        return mIsDebug != null && mIsDebug;
    }

    /**
     * 同步app的debug模式
     * 注意：应该在Application中初始化
     *
     * @param context
     */
    public static void syncIsDebug(Context context) {
        if (mIsDebug == null) {
            mIsDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回app版本名字
     * 对应build.gradle中的versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 返回app版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        String versionCode = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 显示应用详细信息页面
     *
     * @param context
     */
    public static void showInstalledAppDetails(Context context) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(appPkgName, context.getPackageName());
        }
        context.startActivity(intent);
    }

    /**
     * 获取当前App进程的id
     *
     * @return
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 判断当前应用是否是debug状态
     *
     * @param context
     * @return
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建应用程序存储目录
     *
     * @param storageDir
     * @param context
     * @return
     */
    public static String createAppStorageDir(String storageDir, Context context) {
        String dir = null;
        if (SDCardUtil.isSDCardEnable()) {     // 如果存在SD卡
            dir = SDCardUtil.getSDCardPath() + File.separator + storageDir;
        } else {    // 不存在SD卡，就放到缓存文件夹内
            context = context.getApplicationContext();
            dir = context.getCacheDir().getAbsolutePath() + File.separator + storageDir;
        }

        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder.getAbsolutePath();
    }

    /**
     * 应用是否处于后台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    /**
     * 获取当前系统Launcher
     *
     * @param context
     * @return
     */
    public static String getLauncherName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.packageName;
        } else {
            return "";
        }
    }
}
