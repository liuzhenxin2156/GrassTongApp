package com.agridata.grasstong.ui.production;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.data.GrassBean;

import java.util.ArrayList;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 15:49
 * @Description :
 */
public class ProductionServiceActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private ProductionServiceAdapter productionServiceAdapter;
    private ArrayList<GrassBean> mGrassBeanList;
    private ImageView mBackIv;



    /**
     * 启动activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ProductionServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_production_service;
    }

    @Override
    protected void initViews() {
        addData();
        mRecyclerView =findViewById(R.id.recycler_view);
        mBackIv = findViewById(R.id.back_iv);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);
        productionServiceAdapter = new ProductionServiceAdapter(R.layout.item_default_rimless, mGrassBeanList,this);
        mRecyclerView.setAdapter(productionServiceAdapter);
        productionServiceAdapter.refreshDataList(mGrassBeanList);

        mBackIv.setOnClickListener(v -> finish());
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
        String [] strings = {"牧草种植技术服务","牧草使用投喂技术服务","饲草动物疫病诊疗"};
        for (String string : strings) {
            GrassBean grassBean = new GrassBean();
            grassBean.setTitle(string);
            mGrassBeanList.add(grassBean);
        }
    }
}
