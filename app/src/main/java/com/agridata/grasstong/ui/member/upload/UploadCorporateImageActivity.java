package com.agridata.grasstong.ui.member.upload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.ui.member.imagedisplay.MemberImageDisplayActivity;
import com.agridata.grasstong.ui.member.record.MemberRecordActivity;
import com.agridata.grasstong.ui.member.upload.adapter.GridImageAdapter;
import com.agridata.grasstong.ui.mian.MainActivity;
import com.agridata.grasstong.utils.AppUtil;
import com.agridata.grasstong.utils.BitmapUtils;
import com.agridata.grasstong.utils.FileUtil;
import com.agridata.grasstong.utils.GlideEngine;
import com.agridata.grasstong.utils.ImageUtil;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.wegiht.FullyGridLayoutManager;
import com.agridata.grasstong.wegiht.bottomPopupDialog.BottomPopupDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
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
 * @Time : 2021/6/10 11:09
 * @Description :
 */
public class UploadCorporateImageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackIv;
    private RecyclerView recyclerView;
    private ImageView business_license_iv;
    private Button up_business_license_iv_bt;
    private GridImageAdapter adapter;
    private Button upload_bt;
    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();

    public static final int REQUEST_CODE_FROM_GALLERY = 301;
    public static final int REQUEST_CODE_FROM_CAMERA = 302;
    private File mCameraFileBusinessLicense;
    private String imagePathIDCard;


    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, UploadCorporateImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_upload_corporate_image;
    }

    @Override
    protected void initViews() {
        mBackIv = findViewById(R.id.back_iv);
        recyclerView = findViewById(R.id.recyclerview);
        business_license_iv = findViewById(R.id.business_license_iv);
        up_business_license_iv_bt = findViewById(R.id.up_business_license_iv_bt);
        upload_bt   = findViewById(R.id.upload_bt);
        up_business_license_iv_bt.setOnClickListener(this);
        upload_bt.setOnClickListener(this);
        mBackIv.setOnClickListener(this);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum - selectList.size());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = adapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(UploadCorporateImageActivity.this)
                                .themeStyle(R.style.picture_default_style)
                                .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(UploadCorporateImageActivity.this)
                                .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
//                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
//                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
//                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                        PictureSelector.create(UploadCorporateImageActivity.this)
                                .themeStyle(R.style.picture_default_style) // xml设置主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectList);
                        break;
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
            case R.id.up_business_license_iv_bt:
                upImageViewBottomDialog();
                break;
            case R.id.upload_bt:
                ToastUtil.showToast(this,"上传企业形象成功！");
                this.finish();
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
                checkPermission(1);
            } else { //相册选取
                selectImageFromLocal();
            }
        });
        bottomPopupDialog.setOnDismissListener(DialogInterface::dismiss);
    }

    /**
     * 检查权限
     */
    private void checkPermission(int TYPE) {
        PermissionX.init(this).permissions(Manifest.permission.CAMERA).request((allGranted, grantedList, deniedList) -> {
            if (allGranted) {

                selectImageFromCameraBusinessLicense();//去照相
                Logger.d("lzx----》" + "申请权限 成功");
            } else {

                ToastUtil.showToast(UploadCorporateImageActivity.this, "请开启照相机权限！");
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

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> showChoices();

    private void showChoices() {
        final BottomPopupDialog bottomPopupDialog;
        List<String> bottomDialogContents;//弹出列表的内容
        bottomDialogContents = new ArrayList<>();
        bottomDialogContents.add(getString(R.string.take_photo));
        bottomDialogContents.add(getString(R.string.select_from_gallery));
        bottomPopupDialog = new BottomPopupDialog(this, bottomDialogContents);
        bottomPopupDialog.show();
        bottomPopupDialog.showCancelBtn(true);
        bottomPopupDialog.setOnItemClickListener(new BottomPopupDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {//拍照
                    PermissionX.init(UploadCorporateImageActivity.this).permissions(Manifest.permission.CAMERA).request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            //拍照
                            PictureSelector.create(UploadCorporateImageActivity.this)
                                    .openCamera(PictureMimeType.ofAll())
                                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .maxSelectNum(maxSelectNum - selectList.size())//最大选择数量,默认4张
                                    .minSelectNum(1)//// 最小选择数量
                                    .imageSpanCount(4)// 列表每行显示个数
                                    .enableCrop(false)// 是否裁剪 true or false
                                    .compress(true)// 是否压缩
                                    .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .selectionMode(PictureConfig.MULTIPLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        } else {

                            ToastUtil.showToast(UploadCorporateImageActivity.this, "请开启照相机权限！");
                            Logger.d("lzx----》" + "申请权限 失败");
                        }
                    });

                } else {
                    //相册
                    PictureSelector.create(UploadCorporateImageActivity.this)
                            .openGallery(PictureMimeType.ofAll())//相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .maxSelectNum(maxSelectNum - selectList.size())//最大选择数量,默认9张
                            .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                            .minSelectNum(1)//// 最小选择数量
                            .imageSpanCount(4)// 列表每行显示个数
                            .enableCrop(false)// 是否裁剪 true or false
                            .compress(true)// 是否压缩
                            .selectionMode(PictureConfig.MULTIPLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
                            business_license_iv.setImageBitmap(bitmapFormUri);
                            imagePathIDCard = FileUtil.getPathFromUri(this, uri);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                case REQUEST_CODE_FROM_CAMERA:
                    Uri imageUri = Uri.fromFile(this.mCameraFileBusinessLicense);
                    imagePathIDCard = FileUtil.getPathFromUri(this, imageUri);
                    imagePathIDCard = ImageUtil.photo(this,this.mCameraFileBusinessLicense.getPath(), business_license_iv);

                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);
                    Logger.d("lzx----->" + selectList.size() + "");
                    //selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 4.如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                default:
                    break;
            }

        }
    }


}
