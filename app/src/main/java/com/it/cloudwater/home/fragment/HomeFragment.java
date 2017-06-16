package com.it.cloudwater.home.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.constant.DataProvider;
import com.it.cloudwater.home.adapter.BGABannerAdapter;
import com.it.cloudwater.home.bean.BannerDto;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by hpc on 2017/6/16.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_recommend_banner)
    BGABanner homeRecommendBanner;
    @BindView(R.id.hot_commend)
    TextView hotCommend;
    @BindView(R.id.more_commend)
    TextView moreCommend;
    @BindView(R.id.recyclerView_commend)
    RecyclerView recyclerViewCommend;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private ArrayList<BannerDto> bannerList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initListener() {
        recyclerViewCommend.setFocusable(false);
        hotCommend.setText("热门推荐");
        TextPaint hotPaint = hotCommend.getPaint();
        hotPaint.setFakeBoldText(true);
        recyclerViewCommend.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewCommend.setHasFixedSize(true);
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
        homeRecommendBanner.setData(bannerImage,bannerTitle);
    }
}
