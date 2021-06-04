package com.agridata.grasstong.ui.set.accountsafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.sp.UserSp;
import com.agridata.grasstong.ui.changeinfo.ChangeInfoActivity;
import com.agridata.grasstong.ui.forgetPwd.ForgetPwdActivity;
import com.agridata.grasstong.ui.login.LoginActivity;
import com.agridata.grasstong.utils.AppManager;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.ToastUtil;


/**
 * 账号与安全
 */

public class AccountSafeActivity extends BaseActivity {
    private LinearLayout ll_Logout;//注销账号
    private ImageView mBackIv;//返回
    private LinearLayout ll_change_info;
    private LinearLayout  ll_change_pwd;


    public static void start(Context context) {
        Intent intent = new Intent(context, AccountSafeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void initViews() {
        ll_Logout = findViewById(R.id.ll_logout);
        mBackIv = findViewById(R.id.back_iv);

        ll_change_info = findViewById(R.id.ll_change_info);
        ll_change_pwd = findViewById(R.id.ll_change_pwd);

        ll_Logout.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        ll_change_info.setOnClickListener(this);
        ll_change_pwd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.ll_logout://注销账号
                showloginOutDialog();
                break;
            case R.id.ll_change_info://修改信息
                ChangeInfoActivity.start(this);

                break;
            case R.id.ll_change_pwd://修改密码
                ForgetPwdActivity.start(AccountSafeActivity.this);
                break;
            default:
        }
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void showloginOutDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText(R.string.logout);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialogLoginOut.dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().finishAllActivity();
                LoginActivity.start(AccountSafeActivity.this);
                UserSp.getInstance().clear(AccountSafeActivity.this);
                ToastUtil.showToast(AccountSafeActivity.this, "注销成功~");
                exitDialogLoginOut.dismiss();
            }
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

}
