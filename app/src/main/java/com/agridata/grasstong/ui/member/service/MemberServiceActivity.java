package com.agridata.grasstong.ui.member.service;

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
import com.agridata.grasstong.ui.member.manage.MemberManageActivity;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewHolder;

import java.util.ArrayList;

/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/3 15:49
 * @Description :
 */
public class MemberServiceActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private MemberServiceAdapter memberServiceAdapter;
    private ArrayList<GrassBean> mGrassBeanList;
    private ImageView mBackIv;



    /**
     * 启动activity
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, MemberServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_member_service;
    }

    @Override
    protected void initViews() {
        addData();
        mRecyclerView =findViewById(R.id.recycler_view);
        mBackIv = findViewById(R.id.back_iv);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(layoutManager);
        memberServiceAdapter = new MemberServiceAdapter(R.layout.item_default_rimless, mGrassBeanList,this);
        mRecyclerView.setAdapter(memberServiceAdapter);
        memberServiceAdapter.refreshDataList(mGrassBeanList);

        mBackIv.setOnClickListener(v -> finish());

        memberServiceAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        MemberManageActivity.start(MemberServiceActivity.this);
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
        String [] strings = {"会员注册","会员备案","会员管理"};
        for (String string : strings) {
            GrassBean grassBean = new GrassBean();
            grassBean.setTitle(string);
            mGrassBeanList.add(grassBean);
        }
    }
}
