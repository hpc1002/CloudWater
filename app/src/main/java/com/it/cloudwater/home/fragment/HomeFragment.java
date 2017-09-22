package com.it.cloudwater.home.fragment;

import android.content.Intent;
import android.os.Handler;
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
import com.it.cloudwater.bean.BannerBean;
import com.it.cloudwater.bean.GoodsListBean;
import com.it.cloudwater.commodity.DetailActivity;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.constant.DataProvider;
import com.it.cloudwater.home.adapter.BGABannerAdapter;
import com.it.cloudwater.home.bean.BannerDto;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.user.LoginActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by hpc on 2017/6/16.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
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
    private String userId;
    private static final int REFRESH_COMPLETE = 0X110;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    swipeRefresh.setRefreshing(false);
                    break;

            }
        }
    };

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initListener() {
        userId = StorageUtil.getUserId(getActivity());
        recyclerViewCommend.setFocusable(false);
        recyclerViewCommend.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewCommend.setHasFixedSize(true);
        CloudApi.getGoodsListData(0x001, 1, 1000, myCallBack);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                    String body = result.body();
                    GoodsListBean goodsListBean = new Gson().fromJson(body, GoodsListBean.class);
                    goodList = new ArrayList<GoodsListBean.Result.DataList>();
                    if (goodsListBean != null) {
                        int size = goodsListBean.result.dataList.size();
                        for (int i = 0; i < size; i++) {
                            goodList.add(goodsListBean.result.dataList.get(i));
                        }
                    }
                    GoodListAdapter adapter = new GoodListAdapter(R.layout.item_goods, goodList);
                    recyclerViewCommend.setAdapter(adapter);
                    adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("goodsLid", goodList.get(position).lId + "");
                            startActivity(intent);
                        }
                    });
                    adapter.setCallBack(new GoodListAdapter.OnMyClickListener() {
                        @Override
                        public void OnItemClickListener(Integer price, long id, String strGoodsname, String strGoodsimgurl, String strStandard, long goodId) {
                            if (userId.equals("")) {
                                ToastManager.show("请先去登录");
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                return;
                            }
                            Map<String, Object> shopParams = new HashMap<>();
                            shopParams.put("strUserName", "侯鹏成");
                            shopParams.put("lUserId", userId);
                            shopParams.put("nPrice", price);
                            shopParams.put("lGoodsId", goodId);
                            shopParams.put("strGoodsname", strGoodsname);
                            shopParams.put("strGoodsimgurl", strGoodsimgurl);
                            shopParams.put("strStandard", strStandard);
                            JSONObject shopObject = new JSONObject(shopParams);
                            CloudApi.addShopCart(0x002, shopObject, myCallBack);
                        }
                    });
                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("添加购物车成功");
                        } else {
                            ToastManager.show("添加购物车失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x003:
                    String body3 = result.body();
                    BannerBean bannerBean = new Gson().fromJson(body3, BannerBean.class);
                    ArrayList<BannerBean.Result.DataList> dataLists = new ArrayList<>();
                    for (int i = 0; i < bannerBean.result.dataList.size(); i++) {
                        dataLists.add(bannerBean.result.dataList.get(i));
                    }
                    homeRecommendBanner.setAdapter(new BGABannerAdapter(getActivity()));
                    ArrayList<String> bannerTitle = new ArrayList<>();
                    ArrayList<String> bannerImage = new ArrayList<>();
                    for (int i = 0; i < dataLists.size(); i++) {
                        bannerTitle.add(dataLists.get(i).strActivityName);
                        bannerImage.add(Constant.IMAGE_URL + "1/" + dataLists.get(i).lId);
                    }
                    homeRecommendBanner.setData(bannerImage, bannerTitle);
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };


    @Override
    protected void initData() {
        getBannerData();
    }

    private void getBannerData() {

        bannerList = DataProvider.getPictures();
        CloudApi.getLunbo(0x003, myCallBack);

    }

    @Override
    public void onRefresh() {
//        CloudApi.getLunbo(0x003, myCallBack);
        CloudApi.getGoodsListData(0x001, 1, 8, myCallBack);

    }
}
