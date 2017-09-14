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
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String userId;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initListener() {
        userId= StorageUtil.getUserId(getActivity());
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
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();

//                    parseData(body);
                    GoodsListBean goodsListBean = new Gson().fromJson(body, GoodsListBean.class);
                    goodList = new ArrayList<GoodsListBean.Result.DataList>();
                    if (goodsListBean!=null){
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
                            intent.putExtra("goodsLid", goodList.get(position).lId+"");
                            startActivity(intent);
                        }
                    });
                    adapter.setCallBack(new GoodListAdapter.OnMyClickListener() {
                        @Override
                        public void OnItemClickListener(Integer price, long id, String strGoodsname, String strGoodsimgurl, String strStandard,long goodId) {
                            ToastManager.show(strGoodsname);
                            Map<String, Object> shopParams = new HashMap<>();
                            shopParams.put("strUserName","侯鹏成");
                            shopParams.put("lUserId",userId);
                            shopParams.put("nPrice",price);
                            shopParams.put("lGoodsId",goodId);
                            shopParams.put("strGoodsname",strGoodsname);
                            shopParams.put("strGoodsimgurl",strGoodsimgurl);
                            shopParams.put("strStandard",strStandard);
                            JSONObject shopObject = new JSONObject(shopParams);
                            CloudApi.addShopCart(0x002,shopObject,myCallBack);
                        }
                    });
                    break;
                   case 0x002:
                       String body2 = result.body();
                       try {
                           JSONObject jsonObject = new JSONObject(body2);
                           String resCode = jsonObject.getString("resCode");
                           if (resCode.equals("0")){
                               ToastManager.show("添加购物车成功");
                           }else{
                               ToastManager.show("添加购物车失败");
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       break;
                case 0x003:
                    String body3 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body3);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")){
                            JSONObject result1 = jsonObject.getJSONObject("result");
                            JSONArray dataList = result1.getJSONArray("dataList");
                            JSONObject jsonObject1 = new JSONObject(dataList.get(0).toString());
                            int lId = jsonObject1.getInt("lId");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    private void parseData(String json) {



    }

    @Override
    protected void initData() {
        getBannerData();
    }

    private void getBannerData() {

        bannerList = DataProvider.getPictures();
        CloudApi.getLunbo(0x003,3,1,myCallBack);
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
