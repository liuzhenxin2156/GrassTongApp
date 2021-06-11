package com.agridata.grasstong.ui.member.registration;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.data.UserInfo;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.ToastUtil;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/9 17:16
 * @Description :
 */
public class UserRegistrationActivity extends BaseActivity {

    private EditText user_name_et;
    private EditText id_card_et;
    private EditText phone_num_et;
    private EditText verification_code_et;
    private Button vip_register_btn;
    private TextView mGet_Verification_Code_Tv;

    private ImageView back_iv;

    private MyCountDownTimer mCountDownTimer;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserRegistrationActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_registration;
    }

    @Override
    protected void initViews() {
        user_name_et = findViewById(R.id.user_name_et);
        id_card_et = findViewById(R.id.id_card_et);
        phone_num_et = findViewById(R.id.phone_num_et);
        verification_code_et = findViewById(R.id.verification_code_et);

        vip_register_btn = findViewById(R.id.vip_register_btn);
        mGet_Verification_Code_Tv = findViewById(R.id.verification_code_tv);
        back_iv = findViewById(R.id.back_iv);


        back_iv.setOnClickListener(this);
        mGet_Verification_Code_Tv.setOnClickListener(this);
        vip_register_btn.setOnClickListener(this);

        this.mCountDownTimer = new MyCountDownTimer(60 * 1000, 1000, this.mGet_Verification_Code_Tv);
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
            case R.id.vip_register_btn://注册
                if (checkInfo()){
                    UserInfo userInfo = new UserInfo();
                    userInfo.name = user_name_et.getText().toString();
                    userInfo.idCard = id_card_et.getText().toString();
                    userInfo.phone = phone_num_et.getText().toString();
                    this.finish();
                    ToastUtil.showToast(this,"恭喜您，注册成功~");
                }
                break;
            case R.id.verification_code_tv://判断验证码
                if (TextUtils.isEmpty(phone_num_et.getText().toString().trim())) {
                    ToastUtil.showToast(this, R.string.hint_phone_num);
                } else if (!StrUtil.isMobileNumber(phone_num_et.getText().toString().trim())) {
                    ToastUtil.showToast(this, R.string.please_enter_sure_phone_num);
                } else {
                    if (this.mCountDownTimer != null) {
                        this.mCountDownTimer.start();
                    }
                }
                break;
            default:
        }
    }

    private boolean checkInfo() {
        if (StrUtil.isEmpty(user_name_et.getText().toString())) {
            ToastUtil.showToast(this, "请输入姓名");
            return false;
        }
        if (StrUtil.isEmpty(id_card_et.getText().toString())) {
            ToastUtil.showToast(this, "请输入身份证件号码");
            return false;
        }
        if (StrUtil.isEmpty(phone_num_et.getText().toString())) {
            ToastUtil.showToast(this, "请输入手机号");
            return false;
        }
        if (StrUtil.isEmpty(verification_code_et.getText().toString())) {
            ToastUtil.showToast(this, "请输入验证码");
            return false;
        }


        return true;
    }
    /**
     * 倒计时器
     */
    private class MyCountDownTimer extends CountDownTimer {

        private TextView mTextView;

        /**
         * 构造方法
         *
         * @param millisInFuture    倒计时的总时间, 单位ms
         * @param countDownInterval 倒计时的间隔时间, 单位ms
         * @param textView          倒计时的view
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            @SuppressLint("StringFormatMatches")
            String content = String.format(getString(R.string.sms_time), (millisUntilFinished / 1000));
            this.mTextView.setEnabled(false);
            this.mTextView.setText(content);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            this.mTextView.setEnabled(true);
            this.mTextView.setText(getString(R.string.retry_for_valid_code));
        }
    }
}
