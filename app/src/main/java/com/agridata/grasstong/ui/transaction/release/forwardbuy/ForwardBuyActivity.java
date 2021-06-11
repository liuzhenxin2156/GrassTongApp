package com.agridata.grasstong.ui.transaction.release.forwardbuy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.utils.AppConstants;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.BitmapUtils;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.ImageUtil;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.TimerDelay;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.wegiht.bottomPopupDialog.BottomPopupDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/11 14:01
 * @Description :
 */
public class ForwardBuyActivity extends BaseActivity implements View.OnClickListener {


    private EditText product_varieties_et;
    private EditText delivery_date_et;
    private EditText num_et;
    private EditText trading_locations_et;
    private ImageView  back_iv;
    private Button up_buy_info_btn;



    public static void start(Context context) {
        Intent intent = new Intent(context, ForwardBuyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forward_buy;
    }

    @Override
    protected void initViews() {
        back_iv = findViewById(R.id.back_iv);

        num_et = findViewById(R.id.num_et);
        trading_locations_et = findViewById(R.id.trading_locations_et);
        up_buy_info_btn = findViewById(R.id.up_buy_info_btn);

        up_buy_info_btn.setOnClickListener(this);
        back_iv.setOnClickListener(this);


    }

    @Override
    protected void initDatas() {



    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.up_buy_info_btn:
                ToastUtil.showToast(this, "恭喜你！远期订单生产发布成功");
                finish();
                break;
            default:
        }
    }

}
