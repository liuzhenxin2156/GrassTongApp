package com.agridata.grasstong.data;





import com.agridata.grasstong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 56454
 */
public class BannerData {

    public BannerData(int imageRes) {
        this.imageRes = imageRes;
    }

    public int imageRes;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }


    public static List<BannerData> getData() {
        List<BannerData> list = new ArrayList<>();
        list.add(new BannerData(R.drawable.ic_launcher_background));
        list.add(new BannerData(R.drawable.ic_launcher_background));
        list.add(new BannerData(R.drawable.ic_launcher_background));
        list.add(new BannerData(R.drawable.ic_launcher_background));
        list.add(new BannerData(R.drawable.ic_launcher_background));
        return list;
    }

}
