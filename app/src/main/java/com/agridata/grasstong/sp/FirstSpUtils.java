package com.agridata.grasstong.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.grasstong.base.MyApplication;
import com.agridata.grasstong.utils.AppConstants;


public class FirstSpUtils {

    private SharedPreferences mSharedPreferences;

    private FirstSpUtils() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("first_sp", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static FirstSpUtils getInstance() {
        return UserHolder.INSTANCE;
    }

    private static class UserHolder {
        private static final FirstSpUtils INSTANCE = new FirstSpUtils();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 保存boolean 值 是否首次显示dialog
     * @param FirstShow
     */
    public  void  setFirstShow(Boolean FirstShow){
        mSharedPreferences.edit().putBoolean(AppConstants.SP.IS_SHOW_SERVICE, FirstShow ).apply();
    }

    public  Boolean getFirstShow(){
        return mSharedPreferences.getBoolean(AppConstants.SP.IS_SHOW_SERVICE, false);
    }

    public  void  setNoticeShow(Boolean noticeShow){
        mSharedPreferences.edit().putBoolean(AppConstants.SP.NOTICE, noticeShow).apply();
    }

    public  Boolean getNoticeShow(){
        return mSharedPreferences.getBoolean(AppConstants.SP.NOTICE, false);
    }
}
