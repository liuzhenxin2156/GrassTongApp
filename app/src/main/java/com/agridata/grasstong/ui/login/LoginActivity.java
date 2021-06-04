package com.agridata.grasstong.ui.login;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.sp.UserSp;
import com.agridata.grasstong.ui.forgetPwd.ForgetPwdActivity;
import com.agridata.grasstong.ui.mian.MainActivity;
import com.agridata.grasstong.ui.register.RegisterActivity;
import com.agridata.grasstong.ui.smslogin.SmsLoginActivity;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.GlideUtil;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * @author 56454
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity---->";


    private ImageView back_iv;
    private EditText account_et;
    private EditText password_et;
    private ImageView password_look_iv;
    private Button login_btn;
    private TextView login_sms_verification_tv;
    private TextView forgot_password_tv;
    private TextView go_register_tv;
    private boolean mIsShowPwd = true;
    private String mAccount;
    private String mPassword;
    private ImageView head_iv;


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.acticity_login;
    }

    @Override
    protected void initViews() {
        back_iv = findViewById(R.id.back_iv);
        account_et = findViewById(R.id.account_et);
        password_et = findViewById(R.id.password_et);
        password_look_iv = findViewById(R.id.password_look_iv);
        login_btn = findViewById(R.id.login_btn);
        login_sms_verification_tv = findViewById(R.id.login_sms_verification_tv);
        forgot_password_tv = findViewById(R.id.forgot_password_tv);

        go_register_tv = findViewById(R.id.go_register_tv);
        head_iv = findViewById(R.id.head_iv);

        back_iv.setOnClickListener(this);
        login_sms_verification_tv.setOnClickListener(this);
        forgot_password_tv.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        go_register_tv.setOnClickListener(this);
        password_look_iv.setOnClickListener(this);

        //设置可输入最大字数，超过最大字数时提醒
        this.password_et.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                password_look_iv.setVisibility(editable.length() > 0 ? View.VISIBLE : View.GONE);
                editStart = password_et.getSelectionStart();
                editEnd = password_et.getSelectionEnd();
                if (temp.length() > 20) {
                    editable.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    password_et.setText(editable);
                    password_et.setSelection(tempSelection);
                }
            }
        });
        this.password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String account = password_et.getText().toString().trim();
                if (hasFocus) {
                    password_look_iv.setVisibility(account.length() > 0 ? View.VISIBLE : View.GONE);
                } else {
                    password_look_iv.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     */
    @Override
    protected void initDatas() {
        String photoPath = UserSp.getInstance().getPhotoPath();
        if (!TextUtils.isEmpty(photoPath)){
            File fileByPath = FileUtil.getFileByPath(photoPath);
            Logger.d("lzx" +fileByPath.getAbsolutePath() + "fileByPath");
            GlideUtil.loadCircleImage( fileByPath,this,head_iv,R.drawable.ic_user_face_default);//加载URL图片
            Logger.d("lzx" + photoPath);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_iv://返回
                finish();
                break;
            case R.id.go_register_tv://注册
                RegisterActivity.start(this);
                break;
            case R.id.login_sms_verification_tv://验证码登录
               SmsLoginActivity.start(this);

                break;
            case R.id.forgot_password_tv://忘记密码
                ForgetPwdActivity.start(this);
                break;

            case R.id.password_look_iv://眼睛
                if (this.mIsShowPwd) {
                    this.password_et.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    this.password_look_iv.setImageResource(R.drawable.ic_eye);
                    this.mIsShowPwd = false;
                } else {
                    this.password_et.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    this.password_look_iv.setImageResource(R.drawable.ic_close_eye);
                    this.mIsShowPwd = true;
                }
                // 切换后将EditText光标置于末尾
                if (!TextUtils.isEmpty(this.password_et.getText())) {
                    this.password_et.setSelection(this.password_et.getText().length());
                }
                break;
            case R.id.login_btn://登录
//                if (checkInfo()) {
//
//                }
                MainActivity.start(this);
                UserSp.getInstance().setIsLogin(true);
                break;
            default:
        }
    }

    /**
     * 校验信息
     *
     * @return
     */
    private boolean checkInfo() {
        mAccount = this.account_et.getText().toString().trim();
        mPassword = this.password_et.getText().toString().trim();
        if (StrUtil.isEmpty(this.mAccount)) {
            ToastUtil.showToast(this, R.string.hint_phone_num);
            return false;
        }
        if (StrUtil.isEmpty(this.mPassword)) {
            ToastUtil.showToast(this, R.string.hint_password_num);
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
