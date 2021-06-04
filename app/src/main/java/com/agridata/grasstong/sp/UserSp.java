package com.agridata.grasstong.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.grasstong.base.MyApplication;
import com.agridata.grasstong.data.UserInfo;
import com.agridata.grasstong.utils.AppConstants;
import com.agridata.grasstong.utils.GsonUtil;


/**
 * @ProjectName : NewApp
 * @Author :
 * @Time : 2021/1/22 14:48
 * @Description :
 */
public class UserSp {

    private SharedPreferences mSharedPreferences;

    private UserSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("user_sp", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static UserSp getInstance() {
        return UserHolder.INSTANCE;
    }

    private static class UserHolder {
        private static final UserSp INSTANCE = new UserSp();
    }

    public  void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        editor.commit();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 保存图片路径
     * @param photoPath
     */
    public  void  setPhotoPath(String photoPath){
        mSharedPreferences.edit().putString(AppConstants.SP.PHOTOPATH, photoPath ).apply();
    }

    public  String getPhotoPath(){
        return mSharedPreferences.getString(AppConstants.SP.PHOTOPATH, "");
    }



    /**
     * 判断是否登录
     * @param isLogin
     */
    public  void  setIsLogin(Boolean isLogin){
        mSharedPreferences.edit().putBoolean(AppConstants.SP.ISLOGIN, isLogin ).apply();
    }

    public  Boolean getIsLogin(){
        return mSharedPreferences.getBoolean(AppConstants.SP.ISLOGIN, false);
    }


    /**
     * 保存 当前登录的用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {
        mSharedPreferences.edit().putString(AppConstants.SP.USERINFO, GsonUtil.toJson(userInfo)).apply();
    }

    /**
     * 获取 当前登录的用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString(AppConstants.SP.USERINFO, null), UserInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
