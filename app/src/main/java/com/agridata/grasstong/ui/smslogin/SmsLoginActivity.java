package com.agridata.grasstong.ui.smslogin;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.ui.phone_verification_code.PhoneVerificationCodeActivity;
import com.agridata.grasstong.utils.EditTextHintUtil;
import com.agridata.grasstong.utils.InputUtil;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.ToastUtil;


/**
 * @author 56454
 * 短信验证码登录    手机验证码
 */
public class SmsLoginActivity extends BaseActivity {
    private ImageView back_iv;
    private EditText account_et;
    private Button next_btn;


    private String mPhoneNum;


    public static void start(Context context) {
        Intent intent = new Intent(context, SmsLoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_sms_login;
    }

    @Override
    protected void initViews() {

        back_iv = findViewById(R.id.back_iv);
        account_et = findViewById(R.id.account_et);
        next_btn = findViewById(R.id.next_btn);
        back_iv.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        EditTextHintUtil.setEditTextHintSize(account_et,getResources().getString(R.string.please_enter_phone_num),16);
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
            case R.id.next_btn://发送验证码
                if (checkInfo()){
                    PhoneVerificationCodeActivity.start(this,account_et.getText().toString().trim(),1);
                }
                break;
            default:
        }
    }

    /**
     * 检测手机号
     * @return
     */
    private boolean checkInfo() {
        mPhoneNum = account_et.getText().toString().trim();
        if (StrUtil.isEmpty(this.mPhoneNum)) {
            ToastUtil.showToast(this, 0, "请输入手机号");
            return false;
        }
        if (!InputUtil.isMobileLegal(this.mPhoneNum)) {
            ToastUtil.showToast(this, 0, "请输入正确的手机号码");
            return false;
        }

        return true;
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
