package com.agridata.grasstong.ui.member.record;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.utils.AppConstants;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.BitmapUtils;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.ImageUtil;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.StrUtil;
import com.agridata.grasstong.utils.TimerDelay;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.wegiht.bottomPopupDialog.BottomPopupDialog;
import com.orhanobut.logger.Logger;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/9 17:39
 * @Description :
 */
public class MemberRecordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackIv;
    private RelativeLayout business_license_rl;
    private RelativeLayout businessLicense_person_rl;
    private RelativeLayout personal_rl;
    private RadioButton company_cb, personal_cb;
    private EditText user_name_et;
    private EditText business_category_et;
    private EditText operating_area_et;
    private TextView type_tv;

    private AppCompatImageView business_license_iv;
    private AppCompatImageView id_card_iv1;
    private AppCompatImageView id_card_iv2;


    private Button business_license_bt;
    private Button up_idcard_iv_bt;
    private Button up_idcard_iv_bt2;

    private Button confirm_record_btn;

    public static final int REQUEST_CODE_FROM_GALLERY = 301;
    public static final int REQUEST_CODE_FROM_CAMERA = 302;

    private int TYPE = 1;
    private String businessLicensePath;
    private String imagePathBusinessLicenseIDCard;
    private String imagePathIDCard;

    private File mCameraFileIdCard;
    private File mCameraFileBusinessLicense;
    private File mCameraFileBusinessLicenseIdCard;


    String[] strings = AppConstants.TYPE.stringsBusinessCategory;
    int positionPersonal = 0;
    String[] stringsPersonal = AppConstants.TYPE.stringsPersonal;
    int position = 0;
    private int typeCb = 0;


    public static void start(Context context) {
        Intent intent = new Intent(context, MemberRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_member_record;
    }

    @Override
    protected void initViews() {
        mBackIv = findViewById(R.id.back_iv);

        business_license_rl = findViewById(R.id.business_license_rl);
        company_cb = findViewById(R.id.company_cb);
        personal_cb = findViewById(R.id.personal_cb);
        user_name_et = findViewById(R.id.user_name_et);
        business_category_et = findViewById(R.id.business_category_et);
        operating_area_et = findViewById(R.id.operating_area_et);
        business_license_iv = findViewById(R.id.business_license_iv);
        id_card_iv1 = findViewById(R.id.id_card_iv1);
        business_license_bt = findViewById(R.id.up_business_license_iv_bt);
        up_idcard_iv_bt = findViewById(R.id.up_idcard_iv_bt);
        confirm_record_btn = findViewById(R.id.confirm_record_btn);
        type_tv = findViewById(R.id.type_tv);

        id_card_iv2 = findViewById(R.id.id_card_iv2);
        up_idcard_iv_bt2 = findViewById(R.id.up_idcard_iv_bt2);

        businessLicense_person_rl = findViewById(R.id.businessLicense_person_rl);
        personal_rl = findViewById(R.id.personal_rl);


        mBackIv.setOnClickListener(this);
        business_category_et.setOnClickListener(this);
        business_license_bt.setOnClickListener(this);
        up_idcard_iv_bt.setOnClickListener(this);
        up_idcard_iv_bt2.setOnClickListener(this);
        confirm_record_btn.setOnClickListener(this);


        setCheckListener();


    }

    @Override
    protected void initDatas() {
        business_category_et.setText(strings[position]);
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
            case R.id.business_category_et://选择企业类别
                if (company_cb.isChecked()) {
                    chooseBusinessCategoryType();
                } else {
                    choosePersonalType();
                }

                break;
            case R.id.up_business_license_iv_bt://上传营业执照
                TYPE = 1;
                upImageViewBottomDialog();
                break;
            case R.id.up_idcard_iv_bt://上传身份证
                TYPE = 2;
                upImageViewBottomDialog();
                break;
            case R.id.up_idcard_iv_bt2://上传身份证
                TYPE = 3;
                upImageViewBottomDialog();
                break;
            case R.id.confirm_record_btn:
                if (checkInfo()) {
                    ToastUtil.showToast(this, "会员备案成功！");
                    this.finish();
                }
                break;
            default:
        }
    }

    public void chooseBusinessCategoryType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("企业类别");
        builder.setSingleChoiceItems(strings, position, (dialog, which) -> {
            business_category_et.setText(strings[which]);
            TimerDelay.setDelay(dialog);
            position = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }

    public void choosePersonalType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("个人内容");
        builder.setSingleChoiceItems(stringsPersonal, positionPersonal, (dialog, which) -> {
            business_category_et.setText(stringsPersonal[which]);
            TimerDelay.setDelay(dialog);
            positionPersonal = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }

    /**
     * checbox 监听
     */
    private void setCheckListener() {
        company_cb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                typeCb = 0;
                personal_cb.setChecked(false);
                personal_rl.setVisibility(View.GONE);
                business_license_rl.setVisibility(View.VISIBLE);
                businessLicense_person_rl.setVisibility(View.VISIBLE);
                type_tv.setText("企业类别");
                business_category_et.setText(strings[position]);
            }
        });
        personal_cb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                typeCb = 1;
                company_cb.setChecked(false);
                business_license_rl.setVisibility(View.GONE);
                businessLicense_person_rl.setVisibility(View.GONE);
                personal_rl.setVisibility(View.VISIBLE);
                type_tv.setText("个人内容");
                business_category_et.setText(stringsPersonal[positionPersonal]);
            }
        });

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
            switch (TYPE) {
                case 1:
                    if (position == 0) {   //拍照
                        checkPermission(1);
                    } else { //相册选取
                        selectImageFromLocal();
                    }
                    break;

                case 2:
                    if (position == 0) {   //拍照
                        checkPermission(2);
                    } else { //相册选取
                        selectImageFromLocal();
                    }
                    break;
                case 3:
                    if (position == 0) {   //拍照
                        checkPermission(2);
                    } else { //相册选取
                        selectImageFromLocal();
                    }
                    break;
                default:
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CODE_FROM_GALLERY: {
                    if (data != null) {
                        Uri uri = data.getData();
                        Bitmap bitmapFormUri = null;
                        try {
                            bitmapFormUri = BitmapUtils.getBitmapFormUri(uri);
                            switch (TYPE) {
                                case 1:
                                    setPhoto(bitmapFormUri);
                                    businessLicensePath = FileUtil.getPathFromUri(this, uri);
                                    File file1 = FileUtil.uriToFile(MemberRecordActivity.this, uri);
                                    break;
                                case 2:
                                    id_card_iv1.setImageBitmap(bitmapFormUri);
                                    imagePathBusinessLicenseIDCard = FileUtil.getPathFromUri(this, uri);
                                    break;
                                case 3:
                                    id_card_iv2.setImageBitmap(bitmapFormUri);
                                    imagePathIDCard = FileUtil.getPathFromUri(this, uri);
                                    break;
                                default:
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

                case REQUEST_CODE_FROM_CAMERA:
                    switch (TYPE) {
                        case 1:
                            Uri imageUri = Uri.fromFile(this.mCameraFileBusinessLicense);
                            businessLicensePath = FileUtil.getPathFromUri(this, imageUri);
                            businessLicensePath = photo(this.mCameraFileBusinessLicense.getPath(), business_license_iv);
                            break;
                        case 2:
                            Uri BusinessLicenseIdCard = Uri.fromFile(this.mCameraFileBusinessLicenseIdCard);
                            imagePathBusinessLicenseIDCard = FileUtil.getPathFromUri(this, BusinessLicenseIdCard);
                            imagePathBusinessLicenseIDCard = photo(this.mCameraFileBusinessLicenseIdCard.getPath(), id_card_iv1);
                            break;
                        case 3:
                            Uri imageUriTransport = Uri.fromFile(this.mCameraFileIdCard);
                            imagePathIDCard = FileUtil.getPathFromUri(this, imageUriTransport);
                            imagePathIDCard = photo(this.mCameraFileIdCard.getPath(), id_card_iv2);
                            break;
                        default:
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private String photo(String imageName, ImageView imageView) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        String localPath = imageName;
        BitmapFactory.decodeFile(localPath, options);
        int oldHeight = options.outHeight;
        int oldWidth = options.outWidth;
        int sampleSize = 1;//默认缩放为1
        //图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((oldHeight / sampleSize > 854) || (oldWidth / sampleSize > 480)) {
            sampleSize *= 2;
        }
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        // 压缩后的bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(localPath, options);
        imageView.setImageBitmap(bitmap);
        String newPath = AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, MemberRecordActivity.this) + "/" + System.currentTimeMillis() + ".jpeg";
        return ImageUtil.compressBitmapToFile(bitmap, newPath);
    }


    private void setPhoto(Bitmap bitmap) {
        business_license_iv.setImageBitmap(bitmap);
    }

    /**
     * 检查权限
     */
    private void checkPermission(int TYPE) {
        PermissionX.init(this).permissions(Manifest.permission.CAMERA).request((allGranted, grantedList, deniedList) -> {
            if (allGranted) {
                switch (TYPE) {
                    case 1:
                        selectImageFromCameraBusinessLicense();//去照相
                        break;
                    case 2:
                        selectImageFromCameraBusinessLicenseIdCard();//去照相
                        break;
                    case 3:
                        selectImageFromCameraIdCard();//去照相
                        break;
                    default:
                }
                Logger.d("lzx----》" + "申请权限 成功");
            } else {

                ToastUtil.showToast(MemberRecordActivity.this, "请开启照相机权限！");
                Logger.d("lzx----》" + "申请权限 失败");
            }
        });
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
    public void selectImageFromCameraBusinessLicense() {
        mCameraFileBusinessLicense = new File(AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, this), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFileUri(this, mCameraFileBusinessLicense));
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    /**
     * 拍照选择图片身份证号
     */
    public void selectImageFromCameraBusinessLicenseIdCard() {
        mCameraFileBusinessLicenseIdCard = new File(AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, this), System.currentTimeMillis() + "Lin" + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFileUri(this, mCameraFileBusinessLicenseIdCard));
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    public void selectImageFromCameraIdCard() {
        mCameraFileIdCard = new File(AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, this), System.currentTimeMillis() + "Lin" + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFileUri(this, mCameraFileIdCard));
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }


    private boolean checkInfo() {
        if (StrUtil.isEmpty(user_name_et.getText().toString())) {
            ToastUtil.showToast(this, "请输入姓名");
            return false;
        }
        if (StrUtil.isEmpty(business_category_et.getText().toString())) {
            if (typeCb == 0) {
                ToastUtil.showToast(this, "请选择企业类别");
            } else {
                ToastUtil.showToast(this, "请选择个人类别");
            }
            return false;
        }
        if (StrUtil.isEmpty(operating_area_et.getText().toString())) {
            ToastUtil.showToast(this, "请填写经营区域");
            return false;
        }
        if (typeCb == 0) {
            if (StrUtil.isEmpty(businessLicensePath)) {
                ToastUtil.showToast(this, "请上传印业执照照片");
                return false;
            }
            if (StrUtil.isEmpty(imagePathBusinessLicenseIDCard)) {
                ToastUtil.showToast(this, "请上传身份证照片");
                return false;
            }
        } else {
            if (StrUtil.isEmpty(imagePathIDCard)) {
                ToastUtil.showToast(this, "请上传身份证照片");
                return false;
            }
        }
        return true;
    }
}
