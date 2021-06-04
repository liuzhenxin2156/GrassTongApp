package com.agridata.grasstong.ui.transaction.output;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.data.GrassBean;
import com.agridata.grasstong.ui.member.service.MemberServiceActivity;
import com.agridata.grasstong.ui.transaction.purchase.GrassPurChaseActivity;
import com.agridata.grasstong.ui.transaction.query.TransactionQueryActivity;
import com.agridata.grasstong.ui.transaction.sale.GrassSaleActivity;
import com.agridata.grasstong.ui.transaction.sale.GrassSaleAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewHolder;

import java.util.ArrayList;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 15:49
 * @Description :
 */
public class OutputTransactionActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private OutputTransactionAdapter outputTransactionAdapter;
    private ArrayList<GrassBean> mGrassBeanList;
    private ImageView mBackIv;



    /**
     * 启动activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, OutputTransactionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_output_transaction;
    }

    @Override
    protected void initViews() {
        addData();
        mRecyclerView =findViewById(R.id.recycler_view);
        mBackIv = findViewById(R.id.back_iv);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);
        outputTransactionAdapter = new OutputTransactionAdapter(R.layout.item_default_rimless, mGrassBeanList,this);
        mRecyclerView.setAdapter(outputTransactionAdapter);
        outputTransactionAdapter.refreshDataList(mGrassBeanList);

        mBackIv.setOnClickListener(v -> finish());

        outputTransactionAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                switch (position){
                    case 0:
                        GrassSaleActivity.start(OutputTransactionActivity.this);
                        break;
                    case 1:
                        GrassPurChaseActivity.start(OutputTransactionActivity.this);
                        break;
                    case 2:
                        TransactionQueryActivity.start(OutputTransactionActivity.this);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

    }


    @Override
    protected void initDatas() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void addData(){
        mGrassBeanList = new ArrayList<>();
        String [] strings = {"种植企业牧草销售","需求企业牧草采购","销售记录"};
        for (String string : strings) {
            GrassBean grassBean = new GrassBean();
            grassBean.setTitle(string);
            mGrassBeanList.add(grassBean);
        }
    }
}
