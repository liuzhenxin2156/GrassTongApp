package com.agridata.grasstong.ui.transaction.release.makeorder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.data.CustomGroupedItem;
import com.agridata.grasstong.ui.transaction.release.buy.WantBuyActivity;
import com.agridata.grasstong.ui.transaction.release.forwardbuy.ForwardBuyActivity;
import com.agridata.grasstong.ui.transaction.release.forwardsell.ForwardSellActivity;
import com.agridata.grasstong.ui.transaction.release.sell.WantSellActivity;
import com.agridata.grasstong.ui.transaction.release.spot.adapter.CustomLinkagePrimaryAdapterConfig;
import com.agridata.grasstong.ui.transaction.release.spot.adapter.ElemeLinkageSecondaryAdapterConfig;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/10 15:17
 * @Description :
 */
public class MakeOrderActivity extends BaseActivity implements CustomLinkagePrimaryAdapterConfig.OnPrimaryItemClickListener, ElemeLinkageSecondaryAdapterConfig.OnSecondaryItemClickListener,View.OnClickListener{

    private ImageView back_iv;
    private LinkageRecyclerView linkageRecyclerView;
    private Button  want_buy_btn;
    private Button   want_sell_btn;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_make_order;
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        back_iv  = findViewById(R.id.back_iv);
        back_iv.setOnClickListener(v -> finish());
        linkageRecyclerView = findViewById(R.id.linkage);
        want_buy_btn = findViewById(R.id.want_buy_btn);
        want_sell_btn = findViewById(R.id.want_sell_btn);

        linkageRecyclerView.init(getCustomGroupItems(), new CustomLinkagePrimaryAdapterConfig(this), new ElemeLinkageSecondaryAdapterConfig(this));
        linkageRecyclerView.setScrollSmoothly(true);
        linkageRecyclerView.setGridMode(true);

        want_buy_btn.setOnClickListener(this);
        want_sell_btn.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.want_buy_btn:
                ForwardBuyActivity.start(MakeOrderActivity.this);
                break;
            case R.id.want_sell_btn:
                ForwardSellActivity.start(MakeOrderActivity.this);
                break;
            default:
        }
    }

    public static List<CustomGroupedItem> getCustomGroupItems() {
        List<CustomGroupedItem> items = new ArrayList<>();
        items.add(new CustomGroupedItem(true, "供方"));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方", ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("供方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(true, "需方"));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));

        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","需方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        items.add(new CustomGroupedItem(new CustomGroupedItem.ItemInfo("需方","供方",ContextCompat.getDrawable(mContext,R.drawable.grass_iv),"梯牧草","北京宝讯科技有限公司","500吨")));
        return items;
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }


    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<CustomGroupedItem.ItemInfo> item) {

    }
}
