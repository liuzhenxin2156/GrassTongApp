package com.agridata.grasstong.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


import com.agridata.grasstong.utils.NetworkStateReceiver;
import com.orhanobut.logger.Logger;


public class CoreService extends Service {

    private static CoreService mInstance;
    /**
     * 单例
     *
     * @return
     */
    public static CoreService getInstance() {
        return mInstance;
    }

    public static void checkServiceIsHealthy(Context c) {
        if (!isServiceRunning(c)) {
            start(c);
        }
    }

    private static boolean isServiceRunning(Context context) {
        boolean ret = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceInfo.service.getPackageName().equals(context.getPackageName()) && CoreService.class.getName().equals(serviceInfo.service.getClassName())) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 启动service
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, CoreService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       Logger.i("CoreService ===》 created");
        super.onCreate();
        mInstance = this;
        NetworkStateReceiver.getInstance().register(this);                      // 注册网络状态变化广播接收器
    }

    private void initListener() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("CoreService ===》 onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onDestroy() {
        NetworkStateReceiver.getInstance().unregister(this);
    }
}
