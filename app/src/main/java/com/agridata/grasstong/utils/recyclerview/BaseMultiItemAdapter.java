package com.agridata.grasstong.utils.recyclerview;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;


import com.agridata.grasstong.utils.recyclerview.entity.MultiItemEntity;

import java.util.List;

/**
 * 多类型的itemAdapter
 *
 * 具体用法请参考：https://github.com/pengzhenjin/BaseRecyclerViewAdapter
 *
 */
public abstract class BaseMultiItemAdapter<T extends MultiItemEntity, VH extends BaseRecyclerViewHolder> extends BaseRecyclerViewAdapter<T, VH> {

    private SparseArray<Integer> mLayouts;

    public BaseMultiItemAdapter(List<T> dataList) {
        super(dataList);
    }

    @Override
    protected int getDefaultItemViewType(int position) {
        int numHeaders = getHeaderViewCount();
        if (position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = super.mDataList.size();
            if (adjPosition < adapterCount) {
                T item = super.mDataList.get(adjPosition);
                if (item != null) {
                    return item.getItemType();
                }
            }
        }
        return super.getDefaultItemViewType(position);
    }

    @Override
    protected VH createDefaultViewHolder(ViewGroup parent, int viewType) {
        return super.createBaseViewHolder(parent, this.getLayoutId(viewType));
    }

    /**
     * 添加item类型
     *
     * @param viewType
     * @param layoutResId
     */
    protected void addItemType(int viewType, @LayoutRes int layoutResId) {
        if (this.mLayouts == null) {
            this.mLayouts = new SparseArray<>();
        }
        this.mLayouts.put(viewType, layoutResId);
    }

    /**
     * 获取布局id
     *
     * @param viewType
     *
     * @return
     */
    private int getLayoutId(int viewType) {
        return this.mLayouts.get(viewType);
    }
}
