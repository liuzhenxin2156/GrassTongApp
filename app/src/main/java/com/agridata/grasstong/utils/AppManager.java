package com.agridata.grasstong.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class AppManager {
    private static volatile AppManager mInstance;
    private Stack<Activity> mActivityStack = new Stack<>();

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (null == mInstance) {
            synchronized (AppManager.class) {
                if (null == mInstance) {
                    mInstance = new AppManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (null != this.mActivityStack) {
            this.mActivityStack.add(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return null;
        }
        return this.mActivityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (null != this.mActivityStack) {
            Activity activity = this.mActivityStack.lastElement();
            this.finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (null != this.mActivityStack && null != activity) {
            if (this.mActivityStack.contains(activity)) {
                this.mActivityStack.remove(activity);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            activity = null;
        }
    }

    /**
     * 获取指定类名的Activity
     */
    public Activity findActivity(Class<? extends Activity>... activityClass) {
        if (null != this.mActivityStack && this.mActivityStack.size() > 0) {
            Iterator<Activity> iterator = mActivityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (Arrays.asList(activityClass).contains(activity.getClass())) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<? extends Activity>... activityClass) {
        if (null != this.mActivityStack && this.mActivityStack.size() > 0) {
            Iterator<Activity> iterator = mActivityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (Arrays.asList(activityClass).contains(activity.getClass())) {
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (null != this.mActivityStack && this.mActivityStack.size() > 0) {
            for (Activity activity : this.mActivityStack) {
                activity.finish();
                Logger.i("finishAllActivity==>" + activity.getComponentName());
            }
            this.mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        this.finishAllActivity();
        this.mActivityStack = null;
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 是否退出应用程序
     *
     * @return
     */
    public boolean isAppExit() {
        return null == this.mActivityStack || this.mActivityStack.isEmpty();
    }

    /**
     * 判断某个activity是否在栈顶
     *
     * @param context
     * @param activityClass 某个activity
     *
     * @return
     */
    public boolean isTopActivity(Context context, Class activityClass) {
        if (null == context || null == activityClass) {
            return false;
        }
        String activityName = activityClass.getName();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = am.getRunningTasks(1);
        if (runningTaskInfoList != null && runningTaskInfoList.size() > 0) {
            ComponentName cpn = runningTaskInfoList.get(0).topActivity;
            if (activityName.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public int getActivitySizes() {
        return mActivityStack.size();
    }
}
