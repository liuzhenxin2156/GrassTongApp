package com.agridata.grasstong.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

public class NetworkStateReceiver extends BroadcastReceiver {

    private static NetworkStateReceiver mInstance;

    /**
     * 网络状态变化监听器
     */
    private List<NetworkStateChangedListener> mNetworkStateChangedListeners = new ArrayList<>();

    /**
     * 单例
     *
     * @return
     */
    public static NetworkStateReceiver getInstance() {
        if (null == mInstance) {
            synchronized (NetworkStateReceiver.class) {
                if (null == mInstance) {
                    mInstance = new NetworkStateReceiver();
                }
            }
        }
        return mInstance;
    }

    /**
     * 广播接收者回调方法
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean isNetAvailable = NetworkUtil.isNetAvailable(context);
            if (this.mNetworkStateChangedListeners != null && !this.mNetworkStateChangedListeners.isEmpty()) {
                for (NetworkStateChangedListener listener : this.mNetworkStateChangedListeners) {
                    listener.onNetworkStateChanged(isNetAvailable);
                }
            }
        }
    }

    /**
     * 添加网络状态变化监听器
     *
     * @param listener
     */
    public void addNetworkStateChangedListener(NetworkStateChangedListener listener) {
        if (!mNetworkStateChangedListeners.contains(listener)) {
            this.mNetworkStateChangedListeners.add(listener);
        }
    }

    /**
     * 移除网络状态变化监听器
     *
     * @param listener
     */
    public void removeNetworkStateChangedListener(NetworkStateChangedListener listener) {
        this.mNetworkStateChangedListeners.remove(listener);
    }

    /**
     * 网络状态变化监听器
     */
    public interface NetworkStateChangedListener {
        /**
         * 网络状态有变化
         *
         * @param isNetAvailable 网络是否可用
         */
        void onNetworkStateChanged(boolean isNetAvailable);
    }

    /**
     * 注册
     *
     * @param context
     */
    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(mInstance, filter);
    }

    /**
     * 取消注册
     *
     * @param context
     */
    public void unregister(Context context) {
        context.getApplicationContext().unregisterReceiver(mInstance);
    }
}
