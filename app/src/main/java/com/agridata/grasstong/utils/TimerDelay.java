package com.agridata.grasstong.utils;

import android.content.DialogInterface;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class TimerDelay {
    /**
     * 延迟消失300毫秒
     * @param dialog
     */
    public static  void  setDelay(DialogInterface dialog){
        Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    dialog.dismiss();
                });
    }
}
