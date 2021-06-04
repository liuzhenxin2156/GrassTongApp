package com.agridata.grasstong.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.agridata.grasstong.sp.ConfigSP;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.CrashHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 *
 * 尽量压缩图片，转为webp格式。删除无用资源，减少包的体积
 * on: 2020/4/21
 * Created By Lzx
 * describe:
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks{

    public static Context mContext;

    private int  mActivityCount; // activity计数器
    //获取消息推送代理示例

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppUtil.syncIsDebug(this);//是否debug
        initLogger();//初始化日志
        ConfigSP.getInstance().setResourcePath(AppUtil.createAppStorageDir(AppUtil.PATH_APP, this)); //设置App资源路径
        initThirdService();
        // 注册App所有Activity的生命周期回调监听器
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 开启异步线程去初始化第三仿
     */
    private void initThirdService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 启动CubeService
                //WxShareAndLoginUtils.getWXAPI(mContext);//初始化wx
                CrashHandler.getInstance().init(mContext);//初始化崩溃日志
                /**
                 * 初始化common库
                 * 参数1:上下文，必须的参数，不能为空
                 * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
                 * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
                 * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
                 * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
                 */
//                UMConfigure.init(mContext,UMConfigure.DEVICE_TYPE_PHONE, "");
//                UMConfigure.setLogEnabled(true);//当打包正式时 必须设置为false
            }
        }.start();
    }




    /**
     * 获取上下文
     */
    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        this.mActivityCount++;
        if (0 == this.mActivityCount - 1) {
            Logger.i("App 从后台回到前台了" + activity.getLocalClassName());
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        this.mActivityCount--;
        if (0 == this.mActivityCount) {
            Logger.i("App 从前台切换到后台了" + activity.getLocalClassName());
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
