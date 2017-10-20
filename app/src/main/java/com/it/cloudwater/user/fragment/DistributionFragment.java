package com.it.cloudwater.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.it.cloudwater.user.OrderDetailActivity;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/9/23.
 */

public class DistributionFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.erv_order_un_com)
    EasyRecyclerView ervOrderUnCom;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    private View view;
    private String userId;
    private Integer nState = 1;
    private ArrayList<OrderListBean.Result.DataList> orderList;
    private RecyclerArrayAdapter<OrderListBean.Result.DataList> orderListAdapter;
    private int nTotal;
    private OrderListBean orderListBean;
    private static final int REFRESH_COMPLETE = 0X110;
    private static final int SWIPE_REFRESH_COMPLETE = 0X111;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    ervOrderUnCom.setRefreshing(false);
                    break;
                case SWIPE_REFRESH_COMPLETE:
                    swipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fr_order_uncom, container, false);
        userId = StorageUtil.getUserId(getActivity());
        return view;
    }

    @Override
    protected void initListener() {
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        ervOrderUnCom.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {

        if (!userId.equals("")) {
            CloudApi.distributionList(0x001, 1, 8, Long.parseLong(userId), 1, myCallBack);
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    mHandler.sendEmptyMessage(SWIPE_REFRESH_COMPLETE);
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            orderList = new ArrayList<>();
                            orderListBean = new Gson().fromJson(body, OrderListBean.class);
                            int size = orderListBean.result.dataList.size();

                            nTotal = orderListBean.result.nTotal;


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
                OrderListViewHolder distributionState = new OrderListViewHolder(parent, getActivity(), "distributionState");
                distributionState.setCallBack(new OrderListViewHolder.allCheck() {
                    @Override
                    public void OnItemClickListener(OrderListBean.Result.DataList data) {
                        long lId = data.lId;
                        int nSendState = data.nSendState;
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra("orderId", lId + "");
                        intent.putExtra("order_sendState", nSendState + "");
                        startActivity(intent);
                    }

                    @Override
                    public void OnToSettleClickListener(OrderListBean.Result.DataList data) {

                    }

                    @Override
                    public void OnDistributionItemClickListener(OrderListBean.Result.DataList data) {

                    }

                    @Override
                    public void OnItemDeleteClickListener(OrderListBean.Result.DataList data) {

                    }
                });
                return distributionState;
            }
        });
        orderListAdapter.addAll(orderList);

//        orderListAdapter.addAll(orderList.subList(orderList.size() - orderListBean.result.dataList.size(), orderList.size()));
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        orderListAdapter.setMore(R.layout.view_more, this);
        orderListAdapter.setNoMore(R.layout.view_nomore);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    int page = 1;

    @Override
    public void onLoadMore() {
        if (!userId.equals("")) {

            if (page < (nTotal / 8 + 1)) {
                page++;
                CloudApi.distributionList(0x001, 1, 8 * page, Long.parseLong(userId), 1, myCallBack);
            } else {
                orderListAdapter.stopMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (!userId.equals("")) {
            CloudApi.distributionList(0x001, 1, 8, Integer.parseInt(userId), 1, myCallBack);
        }
    }
}
