package com.agridata.grasstong.ui.production;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.agridata.grasstong.R;
import com.agridata.grasstong.data.GrassBean;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewHolder;

import java.util.List;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 13:45
 * @Description : 适配器
 */
public class ProductionServiceAdapter extends BaseRecyclerViewAdapter<GrassBean, BaseRecyclerViewHolder> {
    final  private List<GrassBean> mDates;
   final private Context mContext;


    public ProductionServiceAdapter(int layoutResId, List<GrassBean> dataList, Context context) {
        super(layoutResId, dataList);
        mDates = dataList;
        mContext = context;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder viewHolder, GrassBean data, final int position) {
        TextView title = viewHolder.getView(R.id.tv_record);
        ImageView imageView = viewHolder.getView(R.id.iv_record);
        title.setText(data.getTitle());
        switch (position) {
            case 0:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_grass_planting));
                break;
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_grass_feed));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_aniaml_plague_treatment));
                break;
            default:

        }
    }
}