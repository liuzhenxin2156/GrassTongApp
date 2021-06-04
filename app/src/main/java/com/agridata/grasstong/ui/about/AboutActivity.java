package com.agridata.grasstong.ui.about;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.ui.set.agreement.AgreementActivity;
import com.agridata.grasstong.ui.set.mysetting.MySettingActivity;
import com.agridata.grasstong.utils.AppUtil;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/2 17:12
 * @Description :
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView  back_iv;
    private LinearLayout version_ll;
    private TextView  service_agreement_tv;
    private TextView  user_agreement_tv;
    private TextView  privacy_policy_tv;
    private TextView  app_version_tv;
    private TextView version_tv_num;

    /**
     * 启动activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews() {
        back_iv = findViewById(R.id.back_iv);
        version_ll = findViewById(R.id.version_ll);
        service_agreement_tv = findViewById(R.id.service_agreement_tv);
        user_agreement_tv = findViewById(R.id.user_agreement_tv);
        privacy_policy_tv = findViewById(R.id.privacy_policy_tv);
        app_version_tv = findViewById(R.id.app_version_tv);
        version_tv_num = findViewById(R.id.version_tv_num);

        back_iv.setOnClickListener(v -> finish());
        version_ll.setOnClickListener(this);
        service_agreement_tv.setOnClickListener(this);
        user_agreement_tv.setOnClickListener(this);
        privacy_policy_tv.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        String mCurrentVersion = AppUtil.getVersionName(this);
        this.app_version_tv.setText(String.format(getString(R.string.app_version), mCurrentVersion));
        this.version_tv_num.setText("v"+mCurrentVersion);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.version_ll:
                break;
            case R.id.service_agreement_tv:
                AgreementActivity.start(this, 2);
                break;
            case R.id.user_agreement_tv:
                AgreementActivity.start(this, 0);
                break;
            case R.id.privacy_policy_tv:
                AgreementActivity.start(this, 1);
                break;
            default:
                break;
        }


    }
}
