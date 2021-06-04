package com.agridata.grasstong.ui.bulletin;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.ui.set.agreement.AgreementActivity;


/**
 * @ProjectName : NewApp
 * @Author :  lzx
 * @Time : 2021/1/22 17:36
 * @Description : 公告栏
 */
public class BulletinActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back_iv;



    public static void start(Context context) {
        Intent intent = new Intent(context, BulletinActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_bulletin;
    }

    @Override
    protected void initViews() {
        back_iv = findViewById(R.id.back_iv);
        back_iv.setOnClickListener(v -> finish());

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);


    }
    @Override
    protected void initDatas() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
