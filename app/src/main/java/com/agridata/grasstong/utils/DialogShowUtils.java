package com.agridata.grasstong.utils;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.agridata.grasstong.R;


/**
 * @author 56454
 */
public class DialogShowUtils {




    public   static  String[] stringsPoorType = {"全部非贫困户", "全部贫困户", "部分贫困户"};
    public static  int positionPoor = 0;
    /**
     * 贫困户标识
     */
    public static  void showChoosePoorDialog(Activity context, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("贫困户标识");
        builder.setSingleChoiceItems(stringsPoorType, positionPoor, (dialog, which) -> {
            editText.setText(stringsPoorType[which]);
            TimerDelay.setDelay(dialog);
            positionPoor = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
//        params.height = context.getWindowManager().getDefaultDisplay().getHeight();
//        params.height = (int) (ScreenUtils.getScreenHeight(context) * 0.65);
        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }









    //beneficiary_card_type_et://受益人证件类型
    public   static  String[] stringsBeneficiaryCardType = {"组织机构代码证", "税务登记证", "工商登记证","印业执照注册号","统一社会信用代码","其他证件"};
    public static  int positionBeneficiaryCardType = 0;
    /**
     * 受益人证件类型
     */
    public static  void showChooseBeneficiaryCardTypeDialog(Activity context, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("受益人证件类型");
        builder.setSingleChoiceItems(stringsBeneficiaryCardType, positionBeneficiaryCardType, (dialog, which) -> {
            editText.setText(stringsBeneficiaryCardType[which]);
            TimerDelay.setDelay(dialog);
            positionBeneficiaryCardType = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }






    //被保险人证件类型
    public  static  String[] stringsBeInsuredCardType = {"组织机构代码证", "税务登记证", "工商登记证","营业执照注册号","统一社会信用代码","其他证件"};
    public static  int positionBeInsuredCardType = 0;
    /**
     * 被保险人证件类型
     */
    public static  void showChooseBeInsuredCardTypeCardTypeDialog(Activity context, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("被保险人证件类型");
        builder.setSingleChoiceItems(stringsBeInsuredCardType, positionBeInsuredCardType, (dialog, which) -> {
            editText.setText(stringsBeInsuredCardType[which]);
            TimerDelay.setDelay(dialog);
            positionBeInsuredCardType = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }









    //投保人证件类型 insured_card_type_et
    public   static  String[] stringsInsuredCardType = {"身份证", "户口本", "护照","军官证","驾驶执照","返乡证","营业执照注信用代码","组织机构代码证"};
    public static  int positionInsuredCardType = 0;
    /**
     * 投保人证件类型
     */
    public static  void showChooseInsuredCardTypeDialog(Activity context, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("被保险人证件类型");
        builder.setSingleChoiceItems(stringsInsuredCardType, positionInsuredCardType, (dialog, which) -> {
            editText.setText(stringsInsuredCardType[which]);
            TimerDelay.setDelay(dialog);
            positionInsuredCardType = which;
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_border_white));
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context) * 0.85);
        alertDialog.getWindow().setAttributes(params);
    }
}
