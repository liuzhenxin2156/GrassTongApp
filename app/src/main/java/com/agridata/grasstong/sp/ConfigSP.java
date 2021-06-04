package com.agridata.grasstong.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.grasstong.base.MyApplication;
import com.agridata.grasstong.utils.AppConstants;


public class ConfigSP {

    private SharedPreferences mSharedPreferences;

    private ConfigSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("grass_tong_config", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ConfigSP getInstance() {
        return ConfigHolder.INSTANCE;
    }

    private static class ConfigHolder {
        private static final ConfigSP INSTANCE = new ConfigSP();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    /**
     * 设置App资源路径
     *
     * @param resourcePath
     */
    public void setResourcePath(String resourcePath) {
        mSharedPreferences.edit().putString(AppConstants.SP.RESOURCE_PATH, resourcePath).apply();
    }

    /**
     * 获取App资源路径
     *
     * @return
     */
    public String getResourcePath() {
        return mSharedPreferences.getString(AppConstants.SP.RESOURCE_PATH, "");
    }

    /**
     * 保存boolean 值
     * @param FirstShow
     */
    public  void  setAnimShow(Boolean FirstShow){
        mSharedPreferences.edit().putBoolean("AnimShow", FirstShow ).apply();
    }

    public Boolean getAnimShow(){
        return mSharedPreferences.getBoolean("AnimShow", false);
    }


    public void setDeviceId(String deviceId) {
        mSharedPreferences.edit().putString(AppConstants.SP.DEVICE_ID, deviceId).apply();
    }

    public String getDeviceId() {
        return mSharedPreferences.getString(AppConstants.SP.DEVICE_ID, "");
    }
}
