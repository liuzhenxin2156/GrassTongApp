package com.agridata.fragment.home;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.grasstong.R;
import com.agridata.grasstong.data.GrassBean;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewHolder;

import java.util.List;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 13:45
 * @Description :
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<GrassBean, BaseRecyclerViewHolder> {
    private List<GrassBean> mDates;
    private Context mContext;
    private GridLayoutManager mGridLayoutManager;

    public HomeAdapter(int layoutResId, List<GrassBean> dataList, Context context, GridLayoutManager gridLayoutManager) {
        super(layoutResId, dataList);
        mDates = dataList;
        mContext = context;
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, GrassBean data, final int position) {
        TextView title = viewHolder.getView(R.id.tv_record);
        ImageView imageView = viewHolder.getView(R.id.iv_record);
        title.setText(data.getTitle());
        View convertView = viewHolder.getConvertView();
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = mGridLayoutManager.getWidth()/mGridLayoutManager.getSpanCount();
        switch (position) {
            case 0:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_vip_service));
                break;
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_production_t));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_money_service));
                break;
            case 3:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_protion_service));
                break;
            default:

        }
    }
}