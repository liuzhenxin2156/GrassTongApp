package com.agridata.grasstong.data;



import android.graphics.drawable.Drawable;

import com.kunminx.linkage.bean.BaseGroupedItem;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/10 18:28
 * @Description :
 */
public class CustomGroupedItem extends BaseGroupedItem<CustomGroupedItem.ItemInfo> {

    public CustomGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public CustomGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private Drawable imgUrl;
        private String name;
        private String company;
        private String num;

        public ItemInfo(String title, String group) {
            super(title, group);
        }

        public ItemInfo(String title, String group, Drawable imgUrl, String name, String company, String num) {
            super(title, group);
            this.imgUrl = imgUrl;
            this.name = name;
            this.company = company;
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public Drawable getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(Drawable imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
