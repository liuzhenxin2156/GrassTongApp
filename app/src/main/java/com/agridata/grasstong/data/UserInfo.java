package com.agridata.grasstong.data;

/**
 * @ProjectName : NewApp
 * @Author :
 * @Time : 2021/1/22 16:19
 * @Description :
 */
public class UserInfo {

    public  String name;
    public  String idCard;
    public  String sex;
    public  String  age;
    public  String phone;
    public  String address;
    public String  identity;

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
