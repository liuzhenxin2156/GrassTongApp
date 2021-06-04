package com.agridata.grasstong.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agridata.grasstong.utils.rx.RxManager;


/**
 * on: 2020/4/21
 * Created By Lzx
 * describe:基类 Fragment
 */
public abstract class BaseFragment <P extends BasePresenter> extends CubeFragment implements View.OnClickListener{


    protected View mRootView;

    protected P mPresenter;

    private boolean mIsViewPrepared;    // 标识fragment视图已经初始化完毕
    private boolean mHasLoadData;   // 标识已经触发过懒加载数据
    protected RxManager mRxManager = new RxManager();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=21){
            Window window=getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (this.mRootView == null) {
            this.mRootView = inflater.inflate(this.getContentViewId(), container, false);
            this.initView();
            this.initListener();
        }
        return this.mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.mPresenter = this.createPresenter();
        this.mIsViewPrepared = true;
        this.lazyLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.lazyLoadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mRootView != null && this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        this.mIsViewPrepared = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null) {
            this.mPresenter.unSubscribe();
        }
        if (null != this.mRxManager) {
            this.mRxManager.clear();
            this.mRxManager = null;
        }
    }

    /**
     * 懒加载数据
     */
    private void lazyLoadData() {
        if (super.getUserVisibleHint() && !this.mHasLoadData && this.mIsViewPrepared) {
            this.initData();
            this.mHasLoadData = true;
        }
    }


    public RxManager getRxManager() {
        return mRxManager == null ? new RxManager() : mRxManager;
    }
    /**
     * 获取布局文件id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化组件
     */
    protected void initView() {

    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause() {
        super.onPause();

        }
}
