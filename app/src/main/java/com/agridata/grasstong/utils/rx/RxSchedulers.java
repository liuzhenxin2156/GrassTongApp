package com.agridata.grasstong.utils.rx;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * rx线程调度工具类
 *
 */
public class RxSchedulers {

    private static RxSchedulers sInstance = new RxSchedulers();

    //查询某些表时特定自定义的Scheduler  大量查询时Schedules.io&Schedule.newthread会创建很多线程导致内存溢出 2017/10/24 by cloudzhang
    private Scheduler messageLooper;
    private Scheduler contactLooper;
    private Scheduler recentsessionLooper;

    private Handler mMessageHandler;
    private Handler mContactHandler;
    private Handler mRecentHandler;

    public static RxSchedulers getInstance() {
        return sInstance;
    }

    private RxSchedulers() {
        resetMessageLooperr();
        resetContactLooper();
        resetRecentsessionLooper();
    }

    /**
     * 从io线程切换到android主线程
     *
     * @param <T>
     *
     * @return
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public Scheduler getMessageScheduler() {
        return messageLooper;
    }

    public Scheduler getContactScheduler() {
        return contactLooper;
    }

    public Scheduler getRecentsessionLooper() {
        return recentsessionLooper;
    }

    public void resetMessageLooperr() {
        HandlerThread messageDaoHandle = new HandlerThread("[handle_messagedao]");
        messageDaoHandle.start();
        Looper looper = messageDaoHandle.getLooper();
        mMessageHandler = new Handler(looper);
        messageLooper = AndroidSchedulers.from(looper);
    }

    public void resetContactLooper() {
        HandlerThread contactDaoHandle = new HandlerThread("[handle_contactdao]");
        contactDaoHandle.start();
        Looper contactDaoHandleLooper = contactDaoHandle.getLooper();
        mContactHandler = new Handler(contactDaoHandleLooper);
        contactLooper = AndroidSchedulers.from(contactDaoHandleLooper);
    }

    public void resetRecentsessionLooper() {
        HandlerThread recent = new HandlerThread("[handle_recentdao]");
        recent.start();
        Looper recentlooper = recent.getLooper();
        mRecentHandler = new Handler(recentlooper);
        recentsessionLooper = AndroidSchedulers.from(recentlooper);
    }

    public Handler getmMessageHandler() {
        return mMessageHandler;
    }

    public Handler getmContactHandler() {
        return mContactHandler;
    }

    public Handler getmRecentHandler() {
        return mRecentHandler;
    }
}
