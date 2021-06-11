package com.agridata.grasstong.ui.transaction.release.buy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.utils.AppConstants;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.BitmapUtils;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.ImageUtil;
import com.agridata.grasstong.utils.KeyBoardUtil;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.TimerDelay;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.wegiht.bottomPopupDialog.BottomPopupDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/11 14:01
 * @Description :
 */
public class WantBuyActivity extends BaseActivity implements View.OnClickListener {

    String[] stringsProductName = AppConstants.TYPE.stringProductName;
    int positionProductName = 0;


    String[] stringNumType = AppConstants.TYPE.stringNumType;
    int positionNumType = 0;

    private ImageView test_report_iv;
    private Button up_test_report_iv_bt;
    private EditText company_name_et;
    private EditText product_name_et;
    private EditText num_et;
    private AutoCompleteTextView specification_et;
    private EditText trading_locations_et;
    private Button up_buy_info_btn;
    private ImageView back_iv;

    public static final int REQUEST_CODE_FROM_GALLERY = 301;
    public static final int REQUEST_CODE_FROM_CAMERA = 302;
    private File mCameraFile;
    private String imagePathIDCard;

    public static void start(Context context) {
        Intent intent = new Intent(context, WantBuyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_want_buy;
    }

    @Override
    protected void initViews() {
        back_iv = findViewById(R.id.back_iv);
        test_report_iv = findViewById(R.id.test_report_iv);
        up_test_report_iv_bt = findViewById(R.id.up_test_report_iv_bt);
        company_name_et = findViewById(R.id.company_name_et);
        product_name_et = findViewById(R.id.product_name_et);
        num_et = findViewById(R.id.num_et);
        specification_et = findViewById(R.id.specification_et);
        trading_locations_et = findViewById(R.id.trading_locations_et);
        up_buy_info_btn = findViewById(R.id.up_buy_info_btn);

        back_iv.setOnClickListener(this);
        up_test_report_iv_bt.setOnClickListener(this);
        product_name_et.setOnClickListener(this);
        specification_et.setOnClickListener(this);
        num_et.setOnClickListener(this);
        up_buy_info_btn.setOnClickListener(this);


        specification_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            KeyBoardUtil.closeKeyboard(WantBuyActivity.this);
                        });
            }
        });
        String[] country = getResources().getStringArray(R.array.country_array);
        //3.实例化数组适配器对象
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WantBuyActivity.this,
                android.R.layout.simple_spinner_dropdown_item,//系统提供好的布局文件,即TextView控件
                country//数据源
        );

        //4.设置当前控件的适配器对象adapter
        specification_et.setAdapter(adapter);


    }

    @Override
    protected void initDatas() {
        product_name_et.setText(stringsProductName[positionProductName]);
        num_et.setText(stringNumType[positionNumType]);


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
            case R.id.up_test_report_iv_bt:
                upImageViewBottomDialog();
                break;
            case R.id.product_name_et://产品名称
                chooseProductNameType();
                break;
            case R.id.num_et://数量
                chooseNumType();
                break;
            case R.id.up_buy_info_btn:
                ToastUtil.showToast(this, "恭喜你！购买信息发布成功");
                finish();
                break;
            default:
        }
    }

    /**
     * 产品名称
     */
    public void chooseProductNameType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("企业类别");
        builder.setSingleChoiceItems(stringsProductName, positionProductName, (dialog, which) -> {
            product_name_et.setText(stringsProductName[which]);
            TimerDelay.setDelay(dialog);
            positionProductName = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }

    public void chooseNumType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("数量");
        builder.setSingleChoiceItems(stringNumType, positionNumType, (dialog, which) -> {
            num_et.setText(stringNumType[which]);
            TimerDelay.setDelay(dialog);
            positionNumType = which;
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

                selectImageFromCameraBusinessLicense();//去照相
                Logger.d("lzx----》" + "申请权限 成功");
            } else {

                ToastUtil.showToast(WantBuyActivity.this, "请开启照相机权限！");
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
        mCameraFile = new File(AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, this), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFileUri(this, mCameraFile));
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
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
                            test_report_iv.setImageBitmap(bitmapFormUri);
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
                    imagePathIDCard = photo(this.mCameraFile.getPath(), test_report_iv);
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
        String newPath = AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, WantBuyActivity.this) + "/" + System.currentTimeMillis() + ".jpeg";
        return ImageUtil.compressBitmapToFile(bitmap, newPath);
    }

}
