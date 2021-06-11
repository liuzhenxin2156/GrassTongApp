package com.agridata.grasstong.ui.transaction.release.spot.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.data.CustomGroupedItem;
import com.bumptech.glide.Glide;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;


/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/10 18:27
 * @Description :
 */
public class ElemeLinkageSecondaryAdapterConfig implements ILinkageSecondaryAdapterConfig<CustomGroupedItem.ItemInfo> {

    private static final int SPAN_COUNT_FOR_GRID_MODE = 2;
    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;




    private OnSecondaryItemClickListener mItemClickListener;


    public ElemeLinkageSecondaryAdapterConfig(OnSecondaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public ElemeLinkageSecondaryAdapterConfig setOnItemClickListener(OnSecondaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.adapter_eleme_secondary_grid_item;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.adapter_eleme_secondary_grid_item;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.default_adapter_linkage_secondary_header_item;
    }

    @Override
    public int getFooterLayoutId() {
        return R.layout.adapter_linkage_empty_footer;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.secondary_header_item;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT_FOR_GRID_MODE;
    }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<CustomGroupedItem.ItemInfo> item) {

        ((TextView) holder.getView(R.id.grass_name_tv)).setText(item.info.getName());

      //  Glide.with(mContext).load(item.info.getImgUrl()).into((ImageView) holder.getView(R.id.iv_grass_img));

        ((TextView) holder.getView(R.id.grass_company_tv)).setText(item.info.getCompany());

        ((TextView) holder.getView(R.id.grass_num_tv)).setText(item.info.getNum());
        ViewGroup viewGroup = holder.getView(R.id.iv_grass_item);
        viewGroup.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onSecondaryItemClick(holder, viewGroup, item);
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<CustomGroupedItem.ItemInfo> item) {

        ((TextView) holder.getView(R.id.secondary_header_item)).setText(item.header);
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<CustomGroupedItem.ItemInfo> item) {

    }



    public interface OnSecondaryItemClickListener {
        /**
         * we suggest you get position by holder.getAdapterPosition
         *
         * @param holder primaryHolder
         * @param item   内容
         */
        void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<CustomGroupedItem.ItemInfo> item);
    }
}
