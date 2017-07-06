package com.it.cloudwater.home.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.adapter.CourseRecommendAdapter;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.CourseRecommendBean;
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
    private ArrayList<CourseRecommendBean.RecommendData.Course.CourseData> recommendList;

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
//                startActivity(new Intent(getActivity(), DetailActivity.class));
//            }
//        });
//        recyclerViewCommend.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<CourseRecommendBean.RecommendData.Course.CourseData>(getActivity()) {
//            @Override
//            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                return new RecommendViewholder(parent);
//            }
//        });


        CloudApi.getRecommendData(0x001, myCallBack);

    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, String result) {
            switch (what) {
                case 0x001:
                    if (recommendList == null) {
                        recommendList = new ArrayList<>();
                    }
                    parseData(result);
                    CourseRecommendAdapter adapter = new CourseRecommendAdapter(R.layout.item_pub_course, recommendList);
                    recyclerViewCommend.setAdapter(adapter);
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
        CourseRecommendBean courseRecommendBean = new Gson().fromJson(json, CourseRecommendBean.class);
        System.out.println(courseRecommendBean);
        for (int i = 0, size = courseRecommendBean.data.size(); i < size; i++) {
            recommendList.add(courseRecommendBean.data.get(i).course.data);
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
