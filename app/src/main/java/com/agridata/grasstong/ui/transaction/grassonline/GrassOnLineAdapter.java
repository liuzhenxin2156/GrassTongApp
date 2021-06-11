package com.agridata.grasstong.ui.transaction.grassonline;

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
 * @Description :
 */
public class GrassOnLineAdapter extends BaseRecyclerViewAdapter<GrassBean, BaseRecyclerViewHolder> {
    final  private List<GrassBean> mDates;
   final private Context mContext;


    public GrassOnLineAdapter(int layoutResId, List<GrassBean> dataList, Context context) {
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
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_purchase_information));
                break;
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_purchase_query));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_purchase_record));
                break;

            default:

        }
    }
}