package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.ShopCartListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.viewholder.ShopCartViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/8/16.
 */

public class ShopCartFragment extends BaseFragment {
    @BindView(R.id.cart_recycler)
    EasyRecyclerView cartRecycler;
    private String userId;
    private ArrayList<ShopCartListBean.Result.DataList> cartLists;
    private RecyclerArrayAdapter<ShopCartListBean.Result.DataList> shopCartAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop_cart, container, false);
    }

    @Override
    protected void initListener() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        userId = StorageUtil.getUserId(getActivity());
        CloudApi.getShopList(0x001, Long.parseLong(userId), myCallBack);
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ShopCartListBean shopCartListBean = new Gson().fromJson(body, ShopCartListBean.class);
                            cartLists = new ArrayList<>();
                            for (int i = 0; i < shopCartListBean.result.dataList.size(); i++) {
                                cartLists.add(shopCartListBean.result.dataList.get(i));
                            }
                            initUi(cartLists);
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

    private void initUi(ArrayList<ShopCartListBean.Result.DataList> cartLists) {
        cartRecycler.setAdapterWithProgress(shopCartAdapter = new RecyclerArrayAdapter<ShopCartListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopCartViewHolder(parent);
            }

        });
        shopCartAdapter.addAll(cartLists);
    }
}
