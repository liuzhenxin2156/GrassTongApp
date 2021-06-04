package com.agridata.fragment.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseFragment;
import com.agridata.grasstong.base.BasePresenter;
import com.agridata.grasstong.data.GrassBean;
import com.agridata.grasstong.ui.finance.service.FinanceServiceActivity;
import com.agridata.grasstong.ui.member.service.MemberServiceActivity;
import com.agridata.grasstong.ui.production.ProductionServiceActivity;
import com.agridata.grasstong.ui.transaction.output.OutputTransactionActivity;
import com.agridata.grasstong.utils.ScreenUtils;
import com.agridata.grasstong.utils.ToastUtil;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.grasstong.utils.recyclerview.BaseRecyclerViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;



/**
 * @ProjectName : Test
 * @Author :
 * @Time : 2021/6/2 14:43
 * @Description :
 */
public class HomeFragment extends BaseFragment  {
    private ImageView  home_bg_iv;
    private RecyclerView mRecyclerView;

    private HomeAdapter homeAdapter;
    private ArrayList<GrassBean> mGrassBeanList;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    protected int getContentViewId() {
          return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }




    @Override
    protected void initView() {
        super.initView();
        addData();
        home_bg_iv =  mRootView.findViewById(R.id.home_bg_iv);
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        ViewGroup.LayoutParams layoutParams = home_bg_iv.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenHeight(requireActivity()) * 0.3);//设置当前控件布局的高度
        home_bg_iv.setLayoutParams(layoutParams);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(),3);
        mRecyclerView.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(R.layout.item_default, mGrassBeanList,requireActivity(),layoutManager);
        mRecyclerView.setAdapter(homeAdapter);
        homeAdapter.refreshDataList(mGrassBeanList);

        homeAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                switch (position){
                    case 0:
                        MemberServiceActivity.start(requireActivity());
                        break;
                    case 1:
                        OutputTransactionActivity.start(requireActivity());
                        break;
                    case 2:
                        FinanceServiceActivity.start(requireActivity());
                        break;
                    case 3://生产服务
                        ProductionServiceActivity.start(requireActivity());
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
    protected void initData() {
        super.initData();
        Logger.i("lzx-----》" +  "执行initData");
    }
    /**
     *  会员服务
     *          生产交易
     *         金融服务
     *          生产服务
     */
    private void addData(){
        mGrassBeanList = new ArrayList<>();
        String [] strings = {"会员服务","生产交易","金融服务","生产服务"};
        for (String string : strings) {
            GrassBean grassBean = new GrassBean();
            grassBean.setTitle(string);
            mGrassBeanList.add(grassBean);
        }
    }

}
