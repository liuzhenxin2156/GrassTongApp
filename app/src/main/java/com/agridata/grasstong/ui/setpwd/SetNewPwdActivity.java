package com.agridata.grasstong.ui.setpwd;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.ui.login.LoginActivity;
import com.agridata.grasstong.utils.AppManager;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.ToastUtil;


/**
 * @ProjectName : NewApp
 * @Author : lzx
 * @Time : 2021/1/22 11:23
 * @Description : 设置密码
 */
public class SetNewPwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText mNewPwdCet;//输入新密码
    private EditText mNewPwdAgainCet;//再次输入新密码
    private ImageView mBackIv;
    private Button mAffirmBtn;
    private static final int MAX_LENGTH_LIMIT = 20; // 最大长度限制
    private static final int MIX_LENGTH_LIMIT = 6; // 最小长度限制
    private String mPhoneNum;
    private String mCodeNum;
    private boolean chinese;

    /**
     * 启动设置密码界面
     *
     * @param context
     */
    public static void start(Context context, String mobile, String code) {
        Intent intent = new Intent(context, SetNewPwdActivity.class);
        intent.putExtra("mobile", mobile);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }

    /**
     * 启动验证码界面
     *
     * @param context
     */
    public static void start(Context context, String mobile) {
        Intent intent = new Intent(context, SetNewPwdActivity.class);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return  null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_new_pwd;
    }

    @Override
    protected void initViews() {
        this.getArguments();
        this.mBackIv = (ImageView) findViewById(R.id.back_iv);
        this.mNewPwdCet = findViewById(R.id.input_new_pwd_cet);
        this.mNewPwdAgainCet = findViewById(R.id.input_new_pwd_again_cet);
        this.mAffirmBtn = (Button) findViewById(R.id.confirm_btn);
        this.mNewPwdCet.setLongClickable(false);
        this.mNewPwdCet.setTextIsSelectable(false);
        this.mNewPwdAgainCet.setLongClickable(false);
        this.mNewPwdAgainCet.setTextIsSelectable(false);
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mPhoneNum = this.getIntent().getStringExtra("mobile");
        this.mCodeNum = this.getIntent().getStringExtra("code");
    }

    @Override
    protected void initDatas() {
        this.mAffirmBtn.setOnClickListener(this);
        this.mBackIv.setOnClickListener(this);
        //设置密码不能超过20位数
        this.mNewPwdCet.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = mNewPwdCet.getSelectionStart();
                editEnd = mNewPwdCet.getSelectionEnd();
                if (temp.length() > MAX_LENGTH_LIMIT) {
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    mNewPwdCet.setText(s);
                    mNewPwdCet.setSelection(tempSelection);
                }
            }
        });
        this.mNewPwdAgainCet.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = mNewPwdAgainCet.getSelectionStart();
                editEnd = mNewPwdAgainCet.getSelectionEnd();
                if (temp.length() > MAX_LENGTH_LIMIT) {
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    mNewPwdCet.setText(s);
                    mNewPwdCet.setSelection(tempSelection);
                }
            }
        });
    }




    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.confirm_btn://按钮
                checkModifyPWD();
                break;
            default:
                break;
        }
    }

    /**
     * 检测密码
     */
    private void checkModifyPWD() {
        String newPwd = this.mNewPwdCet.getText().toString();
        String newPwdAgain = this.mNewPwdAgainCet.getText().toString();
        if (!newPwd.isEmpty() && !newPwdAgain.isEmpty()) {
            if (!newPwd.equals(newPwdAgain)) {
                ToastUtil.showToast(this, 0, getString(R.string.please_input_new_pwd_forget2));
            } else {
                if (mNewPwdCet.length() < 6 || mNewPwdAgainCet.length() < 6 && !StrUtil.isPasswordLegal(newPwd) || !StrUtil.isPasswordLegal(newPwdAgain)) {
                    ToastUtil.showToast(SetNewPwdActivity.this, R.string.set_pwd_hint_tv);
                } else {
                    char[] chars = newPwd.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        char c = newPwd.charAt(i);
                        chinese = StrUtil.isChinese(c);
                    }
                    if (chinese == true) {
                        ToastUtil.showToast(SetNewPwdActivity.this,  R.string.set_pwd_hint_tv);
                    } else {
                        //密码
                        AppManager.getInstance().finishAllActivity();
                        LoginActivity.start(this);
                        ToastUtil.showToast(this, "密码修改成功");
                    }
                }
            }
        } else {
            if (!newPwd.isEmpty() && newPwdAgain.isEmpty()) {
                ToastUtil.showToast(this, 0, getString(R.string.please_input_new_pwd_forget1));
            } else {
                ToastUtil.showToast(this, 0, getString(R.string.please_input_new_pwd_forget));
            }
        }
    }





}
