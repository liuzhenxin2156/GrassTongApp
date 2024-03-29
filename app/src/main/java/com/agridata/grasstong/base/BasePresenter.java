package com.agridata.grasstong.base;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * presenter基类
 *
 * @author PengZhenjin
 * @date 2017-1-13
 */
public abstract class BasePresenter<V extends BaseView> {

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 绑定的view
     */
    protected V mView;

    /**
     * 构造方法
     *
     * @param context
     * @param view
     */
    public BasePresenter(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    private CompositeSubscription mCompositeSubscription;

    /**
     * 添加订阅
     *
     * @param subscription
     */
    protected void addSubscribe(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    /**
     * 取消订阅
     */
    public void unSubscribe() {
        if (this.mView != null) {
            this.mView = null;
        }
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.clear();
        }
    }

    /**
     * 只取消所有订阅请求，不清空mView（用于取消当前正在执行的请求）
     */
    public void cancelRequest() {
        if (this.mCompositeSubscription != null && this.mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.clear();
        }
    }

    /**
     * 判断是否已经取消了订阅
     *
     * @return
     */
    public boolean isUnSubscribed() {
        return this.mView == null;
    }
}
