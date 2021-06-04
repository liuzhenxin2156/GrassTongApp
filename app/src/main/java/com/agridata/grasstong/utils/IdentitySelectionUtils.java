package com.agridata.grasstong.utils;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.agridata.grasstong.R;


public class IdentitySelectionUtils {
    public static String[] IdentitySelectionType = {"牧草种植企业", "交易企业", "牧草采购企业","其他"};
    public static int positionID = 0;

    /**
     * 贫困户标识
     */
    public static void showIdentitySelectionDialog(Activity context, EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("身份选择");
        builder.setSingleChoiceItems(IdentitySelectionType, positionID, (dialog, which) -> {
            editText.setText(IdentitySelectionType[which]);
            TimerDelay.setDelay(dialog);
            positionID = which;
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
}
