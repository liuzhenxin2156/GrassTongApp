package com.agridata.grasstong.base;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 10:01
 * @Description :
 */
public class CubeFragment extends ImmersionFragment {
    @Override
    public void initImmersionBar() {
       ImmersionBar.with(this).statusBarDarkFont(true).init();//沉浸式状态栏
    }
}
