package com.agridata.grasstong.ui.transaction.grassonline.shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
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
import com.agridata.grasstong.ui.member.registration.UserRegistrationActivity;
import com.agridata.grasstong.ui.member.upload.UploadCorporateImageActivity;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.BitmapUtils;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.ImageUtil;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.wegiht.bottomPopupDialog.BottomPopupDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/11 18:25
 * @Description :
 */
public class OpenShopActivity extends BaseActivity {

    public static final int REQUEST_CODE_FROM_GALLERY = 301;
    public static final int REQUEST_CODE_FROM_CAMERA = 302;
    private File mCameraFile;
    private String imagePathIDCard;


    private EditText user_name_et;
    private EditText id_card_et;
    private EditText phone_num_et;
    private EditText verification_code_et;
    private Button vip_register_btn;
    private TextView mGet_Verification_Code_Tv;

    private ImageView back_iv;

    private MyCountDownTimer mCountDownTimer;

    public static void start(Context context) {
        Intent intent = new Intent(context, OpenShopActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_shop;
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

    /**
     * 底部弹出去拍照还是去选择相册
     */
    private void upImageViewBottomDialog() {
        List<String> bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add("拍照");
        bottomDialogContents.add("相册选取");
        final BottomPopupDialog bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.show();
        bottomPopupDialog.setCancelable(true);
        bottomPopupDialog.setOnItemClickListener((v, position) -> {
            bottomPopupDialog.dismiss();
            if (position == 0) {   //拍照
                checkPermission();
            } else { //相册选取
                selectImageFromLocal();
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        PermissionX.init(this).permissions(Manifest.permission.CAMERA).request((allGranted, grantedList, deniedList) -> {
            if (allGranted) {
                selectImageFromCamera();//去照相
                Logger.d("lzx----》" + "申请权限 成功");
            } else {
                ToastUtil.showToast(OpenShopActivity.this, "请开启照相机权限！");
                Logger.d("lzx----》" + "申请权限 失败");
            }
        });
    }



    /**
     * 回调 图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CODE_FROM_GALLERY: {
                    if (data != null) {
                        Uri uri = data.getData();
                        Bitmap bitmapFormUri = null;
                        try {
                            bitmapFormUri = BitmapUtils.getBitmapFormUri(uri);
                          //  business_license_iv.setImageBitmap(bitmapFormUri);
                            imagePathIDCard = FileUtil.getPathFromUri(this, uri);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                case REQUEST_CODE_FROM_CAMERA:
                    Uri imageUri = Uri.fromFile(this.mCameraFile);
                    imagePathIDCard = FileUtil.getPathFromUri(this, imageUri);
                  //  imagePathIDCard = ImageUtil.photo(this,this.mCameraFile.getPath(), business_license_iv);

                    break;
                default:
                    break;
            }

        }
    }

    /**
     * 从本地选择图片
     */
    public void selectImageFromLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag
        this.startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
    }

    /**
     * 拍照选择营业执照
     */
    public void selectImageFromCamera() {
        mCameraFile = new File(AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, this), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFileUri(this, mCameraFile));
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
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
