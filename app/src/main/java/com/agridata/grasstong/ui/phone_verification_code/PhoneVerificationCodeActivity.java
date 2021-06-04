package com.agridata.grasstong.ui.phone_verification_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.sp.UserSp;
import com.agridata.grasstong.ui.mian.MainActivity;
import com.agridata.grasstong.ui.setpwd.SetNewPwdActivity;
import com.agridata.grasstong.ui.smslogin.SmsLoginActivity;
import com.agridata.grasstong.utils.AppManager;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.wegiht.MyVerificationCodeView;


/**
 * @author 56454
 * 短信验证码 验证
 */
public class PhoneVerificationCodeActivity extends BaseActivity {
    private String mPhoneNum;
    private int mType;
    private MyVerificationCodeView verifyCodeView;
    private ImageView mBackIv;
    private TextView phone_tv;
    private TextView code_tv;
    private String verificationCode;
    private MyCountDownTimer mCountDownTimer;


    public static void start(Context context, String mobile,int type) {
        Intent intent = new Intent(context, PhoneVerificationCodeActivity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    /**
     * 获取参数
     */
    private void getArguments() {
        this.mPhoneNum = this.getIntent().getStringExtra("mobile");
        this.mType = this.getIntent().getIntExtra("type",-1);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_phone_verification_code;
    }

    @Override
    protected void initViews() {
        getArguments();
        verifyCodeView = findViewById(R.id.write_code_edit);
        mBackIv = findViewById(R.id.back_iv);
        phone_tv = findViewById(R.id.phone_tv);
        code_tv = findViewById(R.id.code_tv);
        mBackIv.setOnClickListener(this);
        code_tv.setOnClickListener(this);
        phone_tv.setText(mPhoneNum);
        verifyCodeView.setInputCompleteListener(new MyVerificationCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                String inputContent = verifyCodeView.getInputContent();
                int length = inputContent.length();
                if (length==6){//验证号码输入完毕
                    if (mType==1){// 1 为短信验证码登录
                        MainActivity.start(PhoneVerificationCodeActivity.this);
                        UserSp.getInstance().setIsLogin(true);
                        finish();
                        AppManager.getInstance().finishActivity(SmsLoginActivity.class);
                        return;
                    }
                    if (mType==2){// 2 忘记密码短息验证码   跳转设置密码界面
                        SetNewPwdActivity.start(PhoneVerificationCodeActivity.this,mPhoneNum);
                    }
                    KeyBoardUtil.closeKeyboard(PhoneVerificationCodeActivity.this);
                }
            }

            @Override
            public void deleteContent() {
                //删除
            }
        });
    }



    @Override
    protected void initDatas() {
        phone_tv.setText(mPhoneNum);
        this.mCountDownTimer = new MyCountDownTimer(60 * 1000, 1000, this.code_tv);
        this.mCountDownTimer.start();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.code_tv://重新发送验证码
                if (this.mCountDownTimer != null) {
                    this.mCountDownTimer.start();
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
            this.mTextView.setTextColor(getResources().getColor(R.color.C4));
            this.mTextView.setText(content);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            this.mTextView.setEnabled(true);
            this.mTextView.setTextColor(getResources().getColor(R.color.C4));
            this.mTextView.setText("重新发送");
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = this.getCurrentFocus();
            if (KeyBoardUtil.isShouldHideInput(v, ev)) {
                KeyBoardUtil.closeKeyboard(v);
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
}
