package com.agridata.grasstong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil {

    public   static  String setPhoto(Context context,String imageName, ImageView imageView) {
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
        String newPath = AppUtil.createAppStorageDir(AppUtil.PATH_APP_IMAGE, context) + "/" + System.currentTimeMillis() + ".jpeg";
        return ImageUtil.compressBitmapToFile(bitmap, newPath);
    }
    private static final String TAG = "SDK_Sample.ImageUtil";

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try{
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if(offset <0){
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if(len <=0 ){
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if(offset + len > (int) file.length()){
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len];
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;


    public static class ImageSize {
        public int width  = 0;
        public int height = 0;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 压缩图片，质量压缩
     *
     * @param bmp
     *
     * @return
     */
    public static Bitmap compressImage(Bitmap bmp) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);   // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到bos中
        int options = 100;
        while (bos.toByteArray().length / 1024 > 100 && options > 0) {     // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            bos.reset();    // 重置bos，即：清空bos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, bos);   // 压缩options%，把压缩后的数据存放到bos中
            options -= 10;  // 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(bos.toByteArray());    // 把压缩后的数据bos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 压缩图片到指定目录，质量压缩
     *
     * @param bmp
     * @param outFile
     */
    public static String compressBitmapToFile(Bitmap bmp, String outFile) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int options = 100;
            bmp.compress(Bitmap.CompressFormat.PNG, options, bos); // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到bos中
            while (bos.toByteArray().length / 1024 > 1024 && options > 0) {  // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                bos.reset();    // 重置bos，即：清空bos
                bmp.compress(Bitmap.CompressFormat.JPEG, options, bos);    // 压缩options%，把压缩后的数据存放到bos中
                options -= 10;  // 每次都减少10
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            return outFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存Bitmap图片到指定路径
     *
     * @param bitmap   jpg 格式
     * @param filePath
     */
    public static void saveBitmapToSd(Bitmap bitmap, String filePath) {
        FileOutputStream outputStream = null;
        try {
            File file = new File(filePath);
            if (file.exists() || file.isDirectory()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存图片
     *
     * @param context
     * @param imagePath 图片路径
     * @param quality   图片品质0-100
     *
     * @return
     */
    public static String saveImage(Context context, String imagePath, int quality) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                File parentFile = imageFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                imageFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            Bitmap bitmap = getBitmap(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片
     *
     * @param context
     * @param imagePath 图片路径
     * @param bitmap
     * @param quality   图片品质
     *
     * @return
     */
    public static String saveImage(Context context, String imagePath, Bitmap bitmap, int quality) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                File parentFile = imageFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                imageFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片到相册
     *
     * @param context
     * @param imageFile
     */
    public static void saveImageToGallery(Context context, File imageFile) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 保存图片到相册
     *
     * @param context
     * @param bmp
     * @param storePath 存储路径
     */
    public static void saveImageToGallery(Context context, Bitmap bmp, String storePath) {
        // 首先保存图片
        //String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zuobiao";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            // 通过io流的方式来压缩保存图片
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            // 保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将一个view保存为图片并添加到相册
     *
     * @param view     要保存的view
     * @param filePath 保存路径
     * @param quality  画质
     */
    public static void saveViewToGallery(final Context context, final View view, final String filePath, final int quality) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view.setDrawingCacheEnabled(true);
                    view.buildDrawingCache();
                    Bitmap bmp = view.getDrawingCache(); // 获取view的图片
                    String savePath = ImageUtil.saveImage(context, filePath, bmp, quality);
                    if (filePath != null) {
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri contentUri = Uri.fromFile(new File(savePath));
                        mediaScanIntent.setData(contentUri);
                        context.sendBroadcast(mediaScanIntent);
                    }
                    view.destroyDrawingCache(); // 保存过后释放资源
                } catch (Exception e) {
                    Logger.e("保存view到相册失败");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 打开相册获取图片，调用onActivityResult()获取图片
     *
     * @param activity
     * @param requestCode
     */
    public static void openGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), requestCode);
    }

    /**
     * 打开相机获取图片，调用onActivityResult()获取图片
     *
     * @param activity
     * @param outputFile  输出目录
     * @param requestCode
     */
    public static void openCamera(Activity activity, File outputFile, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param fileName
     *
     * @return
     */
    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException | OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param imagePath
     *
     * @return
     */
    public static Bitmap getBitmap(String imagePath) {
        return BitmapFactory.decodeFile(imagePath);
    }

    /**
     * 获取bitmap
     *
     * @param imageFile
     *
     * @return
     */
    public static Bitmap getBitmap(File imageFile) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 按宽高获取压缩后的bitmap
     *
     * @param imagePath
     * @param width
     * @param height
     *
     * @return
     */
    public static Bitmap getBitmap(String imagePath, int width, int height) {
        BitmapFactory.Options options = getBitmapOptions(imagePath);

        // 编码后bitmap的宽高,bitmap除以屏幕宽度得到压缩比
        int widthRatio = (int) Math.ceil(options.outWidth / (float) width);
        int heightRatio = (int) Math.ceil(options.outHeight / (float) height);

        if (widthRatio > 1 && heightRatio > 1) {
            if (widthRatio > heightRatio) {
                // 压缩到原来的(1/widthRatios)
                options.inSampleSize = widthRatio;
            }
            else {
                options.inSampleSize = heightRatio;
            }
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    /**
     * 获取图片角度
     *
     * @param imagePath
     *
     * @return
     */
    public static int readImageDegree(String imagePath) {
        short degree = 0;
        ExifInterface exifInterface = null;
        int orientation = -1;
        try {
            exifInterface = new ExifInterface(imagePath);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param bitmap 需要旋转的图片
     * @param degree 旋转角度
     *
     * @return
     */
    public static Bitmap rotateImage(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }




    /**
     * 创建缩略图
     *
     * @param context
     * @param originalImagePath 原始图片路径
     * @param thumbImagePath    输出缩略图路径
     * @param targetWidth       输出图片宽度
     * @param quality           输出图片质量
     */
    public static String createThumbnailImage(Context context, String originalImagePath, String thumbImagePath, int targetWidth, int quality) {
        BitmapFactory.Options opts = getBitmapOptions(originalImagePath);
        int[] originalSize = new int[] { opts.outWidth, opts.outHeight };   // 原始图片的高宽
        int[] newSize = scaleImageSize(originalSize, targetWidth);   // 计算原始图片缩放后的宽高
        opts.inSampleSize = calculateInSampleSize(opts, newSize[0], newSize[1]);
        opts.inJustDecodeBounds = false;
        Bitmap scaleBitmap = BitmapFactory.decodeFile(originalImagePath, opts);
        int imageDegree = readImageDegree(originalImagePath);   // 获取图片角度
        if (null != scaleBitmap && imageDegree > 0) {
            scaleBitmap = rotateImage(scaleBitmap, imageDegree);
        }
        Bitmap zoomBitmap = zoomBitmap(scaleBitmap, scaleBitmap.getWidth(), scaleBitmap.getHeight());   // 缩放图片
        return saveImage(context, thumbImagePath, zoomBitmap, quality);    // 保存并返回
    }

    /**
     * 获取缩略图
     *
     * @param context
     * @param imagePath
     * @param targetWidth
     *
     * @return
     */
    public static String createThumbnailImage(Context context, String imagePath, int targetWidth) {
        Bitmap scaleBitmap = decodeScaleImage(imagePath, targetWidth, targetWidth);
        return saveImage(context, imagePath, scaleBitmap, 100); // 保存图片并返回
    }

    /**
     * 图片解析
     *
     * @param imagePath
     * @param targetWidth
     * @param targetHeight
     *
     * @return
     */
    public static Bitmap decodeScaleImage(String imagePath, int targetWidth, int targetHeight) {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(imagePath);
        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, targetWidth, targetHeight);
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap noRotatingBitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions);
        int degree = readImageDegree(imagePath);
        Bitmap rotatingBitmap;
        if (noRotatingBitmap != null && degree != 0) {
            rotatingBitmap = rotateImage(noRotatingBitmap, degree);
            noRotatingBitmap.recycle();
            return rotatingBitmap;
        }
        else {
            return noRotatingBitmap;
        }
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param imgSize
     * @param targetWidth
     *
     * @return
     */
    public static int[] scaleImageSize(int[] imgSize, int targetWidth) {
        if (imgSize[0] <= targetWidth && imgSize[1] <= targetWidth) {
            return imgSize;
        }
        double ratio = targetWidth / (double) Math.max(imgSize[0], imgSize[1]);
        return new int[] { (int) (imgSize[0] * ratio), (int) (imgSize[1] * ratio) };
    }

    /**
     * 缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     *
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newBmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) w / width);
            float scaleHeight = ((float) h / height);
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return newBmp;
    }

    /**
     * 计算样本大小
     *
     * @param options
     * @param targetWidth
     * @param targetHeight
     *
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) targetHeight);
            final int widthRatio = Math.round((float) width / (float) targetWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高           // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取BitmapFactory.Options
     *
     * @param imagePath
     *
     * @return
     */
    public static BitmapFactory.Options getBitmapOptions(String imagePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, opts);
        return opts;
    }

    /**
     * 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param targetBitmap 需要转化bitmap参数
     * @param targetWidth  设置新的宽度
     *
     * @return
     */
    public static Bitmap fitBitmap(Bitmap targetBitmap, int targetWidth) {
        if (targetBitmap == null) {
            return null;
        }
        int width = targetBitmap.getWidth();
        int height = targetBitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) targetWidth) / width;
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap bmp = Bitmap.createBitmap(targetBitmap, 0, 0, width, height, matrix, true);
        if (targetBitmap != null && !targetBitmap.equals(bmp) && !targetBitmap.isRecycled()) {
            targetBitmap.recycle();
            targetBitmap = null;
        }
        return bmp;
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     *
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Bitmap转化为Drawable
     *
     * @param context
     * @param bitmap
     *
     * @return
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     * @param width
     * @param height
     * @param kind      MediaStore.Images.Thumbnails.MICRO_KIND 或 MediaStore.Images.Thumbnails.MINI_KIND
     *
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        if (bitmap == null) {
            return null;
        }

        if (width <= 0 || height <= 0) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        return ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     * @param kind      MediaStore.Images.Thumbnails.MICRO_KIND 或 MediaStore.Images.Thumbnails.MINI_KIND
     *
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int kind) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        return ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    /**
     * 是否是gif图片
     *
     * @param imagePath
     *
     * @return
     */
    public static boolean isGif(String imagePath) {
        String fileExtensionName = FileUtil.getFileExtensionName(imagePath);
        return fileExtensionName.equalsIgnoreCase(".gif");
    }

    /**
     * bitmap转byte[]
     *
     * @param bm
     *
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[]转bitmap
     *
     * @param b
     *
     * @return
     */
    public static Bitmap byte2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        else {
            return null;
        }
    }





    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

}
