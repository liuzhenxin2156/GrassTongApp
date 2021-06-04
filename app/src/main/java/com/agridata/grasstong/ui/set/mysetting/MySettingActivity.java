package com.agridata.grasstong.ui.set.mysetting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.sp.UserSp;
import com.agridata.grasstong.ui.about.AboutActivity;
import com.agridata.grasstong.ui.login.LoginActivity;
import com.agridata.grasstong.ui.set.accountsafe.AccountSafeActivity;
import com.agridata.grasstong.ui.set.agreement.AgreementActivity;
import com.agridata.grasstong.utils.AppManager;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.ToastUtil;


/**
 * 设置
 */
public class MySettingActivity extends BaseActivity {

    private LinearLayout ll_agreement;//用户协议
    private LinearLayout ll_privacy;//隐私政策

    private Button exit_login_bt;
    private ImageView mBackIv;//返回


    private LinearLayout  account_safe_ll;//账号与安全
    private LinearLayout  ll_about;//关于



    /**
     * 启动activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, MySettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId(){
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(){
        ll_agreement = findViewById(R.id.ll_agreement);
        ll_privacy = findViewById(R.id.ll_privacy);
        exit_login_bt = findViewById(R.id.btn_exit_login);
        mBackIv = findViewById(R.id.back_iv);
        account_safe_ll = findViewById(R.id.account_safe_ll);
        ll_about = findViewById(R.id.ll_about);
        mBackIv.setOnClickListener(v -> finish());
        exit_login_bt.setOnClickListener(this);
        ll_agreement.setOnClickListener(this);
        ll_privacy.setOnClickListener(this);
        account_safe_ll.setOnClickListener(this);
        ll_about.setOnClickListener(this);

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
            case R.id.ll_agreement:
                AgreementActivity.start(this, 0);//0为用户服务
                break;
            case R.id.ll_privacy:
                AgreementActivity.start(this, 1);//1为隐私政策
                break;
            case R.id.btn_exit_login:
                showExitLoginDialog();
                break;
            case R.id.account_safe_ll://账号与安全
                AccountSafeActivity.start(this);
                break;
            case R.id.ll_about://
                AboutActivity.start(this);
                break;
            default:
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * 显示退出登录对话框
     */
    private void showExitLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText(R.string.confirm_exit_login);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().finishAllActivity();
                LoginActivity.start(MySettingActivity.this);
                UserSp.getInstance().clear(MySettingActivity.this);

                ToastUtil.showToast(MySettingActivity.this, "退出成功");
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }
}
