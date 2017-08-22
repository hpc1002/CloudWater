package com.it.cloudwater.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.OrderListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hpc on 2017/6/19.
 */

public class UnComOrderFragment extends BaseFragment {
    @BindView(R.id.erv_order_un_com)
    EasyRecyclerView ervOrderUnCom;
    private View view;
    private String userId;
    private Integer nState = 0;
    private ArrayList<OrderListBean.Result.DataList> orderList;
    private RecyclerArrayAdapter<OrderListBean.Result.DataList> orderListAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fr_order_uncom, container, false);
        userId = StorageUtil.getUserId(getActivity());
        return view;
    }

    @Override
    protected void initListener() {
        ervOrderUnCom.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        CloudApi.orderList(0x001, 1, 8, Long.parseLong(userId), nState, myCallBack);
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
                            OrderListBean orderListBean = new Gson().fromJson(body, OrderListBean.class);
                            int size = orderListBean.result.dataList.size();

                            orderList = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                orderList.add(orderListBean.result.dataList.get(i));
                            }
                            initUi(orderList);
                        } else if (resCode.equals("1")) {
                            ToastManager.show("暂无订单");
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

    private void initUi(ArrayList<OrderListBean.Result.DataList> orderList) {
        ervOrderUnCom.setAdapterWithProgress(orderListAdapter = new RecyclerArrayAdapter<OrderListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new OrderListViewHolder(parent);
            }
        });
        orderListAdapter.addAll(orderList);
    }
}
