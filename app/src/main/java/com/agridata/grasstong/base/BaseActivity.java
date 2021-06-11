package com.agridata.grasstong.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.agridata.grasstong.R;
import com.agridata.grasstong.service.CoreService;
import com.agridata.grasstong.utils.AppManager;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.utils.NetworkStateReceiver;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.utils.rx.RxManager;
import com.gyf.immersionbar.ImmersionBar;


/**
 * on: 2020/4/21
 * Created By Lzx
 * describe: Base 基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener,
        NetworkStateReceiver.NetworkStateChangedListener {

    public static Context mContext;
    protected P mPresenter;
    protected Bundle mSavedInstanceState;
    protected RxManager mRxManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CoreService.checkServiceIsHealthy(this);
        AppManager.getInstance().addActivity(this);//加入activity管理站
        super.setContentView(this.getContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); //禁止横屏
        this.mSavedInstanceState = savedInstanceState;
        this.mPresenter = this.createPresenter();//实列P层
        this.mRxManager = new RxManager();//实列RxManager 用于发送消息
        this.initViews();
        this.initDatas();
        this.OnEventMainThread();
        ImmersionBar.with(this).statusBarDarkFont(true).init();//沉浸式状态栏
        NetworkStateReceiver.getInstance().addNetworkStateChangedListener(this);
    }


    /**
     * 获取布局文件id
     *
     * @return
     */
    protected abstract int getContentViewId();

    //初始化View
    protected abstract void initViews();

    //初始化数据
    protected abstract void initDatas();


    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();



    /**
     * RxBus事件处理-主线程
     */
    protected void OnEventMainThread() {

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
    }
    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mRxManager == null) {
            this.mRxManager = new RxManager();
        }
    }
    /**
     * 网络状态变化回调
     *
     * @param isNetAvailable 网络是否可用
     */
    @Override
    public void onNetworkStateChanged(boolean isNetAvailable) {
        if (!isNetAvailable) {
            ToastUtil.showToast(this,"请检查网络是否连接");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 注意 mPresenter+ RxManager + ImmersionBar都要销毁 否则造成内存泄漏
     */
    @Override
    protected void onDestroy() {
        AppManager.getInstance().finishActivity(this);
        if (this.mPresenter != null) {
            this.mPresenter.unSubscribe();
        }
        if (null != this.mRxManager) {
            this.mRxManager.clear();
            this.mRxManager = null;
        }
        ImmersionBar.destroy(this, null);
        NetworkStateReceiver.getInstance().removeNetworkStateChangedListener(this);
        super.onDestroy();


    }


    //使editText点击外部时候失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    KeyBoardUtil.closeKeyboard(v);//软键盘工具类
                    if (editText != null) {
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    EditText editText = null;

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            editText = (EditText) v;
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


}
