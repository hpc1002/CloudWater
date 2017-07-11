package com.it.cloudwater.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.adapter.GoodListAdapter;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.base.BaseQuickAdapter;
import com.it.cloudwater.bean.GoodsListBean;
import com.it.cloudwater.commodity.DetailActivity;
import com.it.cloudwater.constant.DataProvider;
import com.it.cloudwater.home.adapter.BGABannerAdapter;
import com.it.cloudwater.home.bean.BannerDto;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by hpc on 2017/6/16.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_recommend_banner)
    BGABanner homeRecommendBanner;
    @BindView(R.id.recyclerView_commend)
    RecyclerView recyclerViewCommend;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    //    @BindView(R.id.next)
//    Button next;
    private ArrayList<BannerDto> bannerList;
    private ArrayList<GoodsListBean.Result.DataList> goodList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initListener() {
        recyclerViewCommend.setFocusable(false);
        recyclerViewCommend.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewCommend.setHasFixedSize(true);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//            }
//        });


        CloudApi.getGoodsListData(0x001, 1, 4, myCallBack);

    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, String result) {
            switch (what) {
                case 0x001:
                    if (goodList == null) {
                        goodList = new ArrayList<>();
                    }
                    parseData(result);
                    GoodListAdapter adapter = new GoodListAdapter(R.layout.item_goods, goodList);
                    recyclerViewCommend.setAdapter(adapter);
                    adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("goodsLid", goodList.get(position).lId+"");
                            startActivity(intent);
                        }
                    });
            }
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, Object result) {

        }
    };

    private void parseData(String json) {
        GoodsListBean goodsListBean = new Gson().fromJson(json, GoodsListBean.class);
        for (int i = 0, size = goodsListBean.result.dataList.size(); i < size; i++) {
            goodList.add(goodsListBean.result.dataList.get(i));
        }

    }

    @Override
    protected void initData() {
        getBannerData();
    }

    private void getBannerData() {

        bannerList = DataProvider.getPictures();
        homeRecommendBanner.setAdapter(new BGABannerAdapter(getActivity()));
        ArrayList<String> bannerTitle = new ArrayList<>();
        ArrayList<String> bannerImage = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            bannerTitle.add(bannerList.get(i).getBannerTitle());
            bannerImage.add(bannerList.get(i).getImageUrl());
        }
        homeRecommendBanner.setData(bannerImage, bannerTitle);
    }

}
