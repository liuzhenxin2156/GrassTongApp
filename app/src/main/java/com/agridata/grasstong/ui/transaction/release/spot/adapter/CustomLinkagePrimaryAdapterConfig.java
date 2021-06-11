package com.agridata.grasstong.ui.transaction.release.spot.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.agridata.grasstong.R;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/10 17:58
 * @Description :
 */
public class CustomLinkagePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemClickListener mItemClickListener;

    public CustomLinkagePrimaryAdapterConfig(OnPrimaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public CustomLinkagePrimaryAdapterConfig setOnItemClickListener(OnPrimaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_adapter_linkage_primary_item;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group_item;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        TextView tvTitle = ((TextView) holder.mGroupTitle);
        View view = holder.getView(R.id.view_item);
        tvTitle.setText(title);
        LinearLayout mLayout = (LinearLayout) holder.mLayout;


        mLayout.setBackgroundColor(mContext.getResources().getColor(selected ? R.color.colorWhite : R.color.color_108));
        tvTitle.setTextColor(ContextCompat.getColor(mContext, selected ? R.color.C4 : R.color.app_color_black));
        view.setVisibility(selected?View.VISIBLE:View.INVISIBLE);
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        tvTitle.setFocusable(selected);
        tvTitle.setFocusableInTouchMode(selected);
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
        if (mItemClickListener != null) {
            mItemClickListener.onPrimaryItemClick(holder, view, title);
        }
    }

    public interface OnPrimaryItemClickListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param holder primaryHolder
         * @param view   view
         * @param title  groupTitle
         */
        void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title);
    }
}
