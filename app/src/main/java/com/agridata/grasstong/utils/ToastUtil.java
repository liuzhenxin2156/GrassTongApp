package com.agridata.grasstong.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.MyApplication;


public class ToastUtil extends Toast {

    private static Toast mToast;

    public ToastUtil(Context context) {
        super(context);
    }

    /**
     * 自定义Toast样式
     *
     * @description
     */
    public static Toast makeText(Context context, int resId, CharSequence text, int duration) {
        //外界传递的activity context引起大量内存泄漏 对此拦截使用application context
        context = MyApplication.getContext();
        mToast = new Toast(context);

        // 获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.toast_hud, null);

        // 实例化ImageView和TextView对象
        ImageView imageView = (ImageView) layout.findViewById(R.id.ImageView);
        TextView textView = (TextView) layout.findViewById(R.id.message_tv);

        //这里我为了给大家展示就使用这个方面既能显示无图也能显示带图的toast
        //不用显示图片直接写0，显示图片加载ID即可
        if (resId == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(resId);
        }

        textView.setText(text);

        mToast.setView(layout);
        mToast.setDuration(duration);

        return mToast;
    }

    /**
     * @param context    上下文
     * @param imageResId 图片Id
     * @param message    消息
     *                   默认3秒
     */
    public static void showToast(Context context, int imageResId, String message) {
        if (mToast == null) {
            mToast = ToastUtil.makeText(context, imageResId, message, 2000);
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = ToastUtil.makeText(context, 0, message, 2000);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    /**
     * @param context 上下文   为空new  不为空cancel  在重新new
     * @param message 消息
     *                没图片 0
     */
    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = ToastUtil.makeText(context, 0, message, 2000);
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = ToastUtil.makeText(context, 0, message, 2000);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context, String message, int duration) {
        mToast = ToastUtil.makeText(context, 0, message, duration);
        mToast.show();
    }

    public static void Cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public static Toast getToastAndShow(Context context, String message, int duration) {
        Toast mToast = ToastUtil.makeText(context, 0, message, duration);
        mToast.show();
        return mToast;
    }

    public static void showToast(Context context, int imageResId, int messageResId) {
        Toast mToast = ToastUtil.makeText(context, imageResId, context.getResources().getString(messageResId), 3000);
        mToast.show();
    }

    /**
     * @param messageResId 消息String ID
     */
    public static void showToast(Context context, int messageResId) {
        mToast = ToastUtil.makeText(context, 0, context.getResources().getString(messageResId), 3000);
        mToast.show();
    }

    /**
     * @param messageResId 消息String ID
     */
    public static void showToastTime(Context context, int messageResId, int duration) {
        mToast = ToastUtil.makeText(context, 0, context.getResources().getString(messageResId), duration);
        mToast.show();
    }


}
