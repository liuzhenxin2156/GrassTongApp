package com.agridata.grasstong.data;

import androidx.annotation.DrawableRes;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 13:44
 * @Description :
 */
public class GrassBean {
    private String  title;
    private int img;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DrawableRes
    public int getImg() {
        return img;
    }

    public void setImg(@DrawableRes int img) {
        this.img = img;
    }
    @Override
    public String toString() {
        return "RecordData{" +
                "title='" + title + '\'' +
                '}';
    }
}
