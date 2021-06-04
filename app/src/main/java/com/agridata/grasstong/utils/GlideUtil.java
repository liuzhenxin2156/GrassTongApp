package com.agridata.grasstong.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideUtil {


    /**
     * 加载图片
     *
     * @param resourceId
     * @param context
     * @param imageView
     * @param defResourceId
     */
    public static void loadImage(String resourceId, Context context, ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(resourceId).apply(options).into(imageView);


    }
    public static void loadImage(File file, Context context, ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(file).apply(options).into(imageView);


    }
    /**
     * 加载圆形图片

     * @param context
     * @param imageView
     * @param defResourceId
     */
    public static void loadCircleImage(String stringName, final Context context, final ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(stringName)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    public static void loadCircleImage(File file, final Context context, final ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(file)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    public static void loadCircleImage(Uri uri, final Context context, final ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(uri)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    public static void loadCircleImage(Bitmap uri, final Context context, final ImageView imageView, int defResourceId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defResourceId); //添加占位图
        Glide.with(context).load(uri)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 清除内存中的缓存 必须在UI线程中调用
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘中的缓存 必须在后台线程中调用，建议同时clearMemory()
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}
