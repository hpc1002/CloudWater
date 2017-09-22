package com.it.cloudwater.home.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.it.cloudwater.pay.PayActivity;
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

public class UnComOrderFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener {
    @BindView(R.id.erv_order_un_com)
    EasyRecyclerView ervOrderUnCom;
    private View view;
    private String userId;
    private Integer nState = -1;//未支付
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
//                    swipeRefresh.setRefreshing(false);
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
        ervOrderUnCom.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        orderList = new ArrayList<>();
        if (!userId.equals("")) {
            CloudApi.orderList(0x001, 1, 8, Long.parseLong(userId), nState, myCallBack);
        }

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
                OrderListViewHolder orderListViewHolder = new OrderListViewHolder(parent,getActivity());
                orderListViewHolder.setCallBack(new OrderListViewHolder.allCheck() {
                    @Override
                    public void OnItemClickListener(OrderListBean.Result.DataList data) {
                        Intent intent = new Intent(getActivity(), PayActivity.class);
                        intent.putExtra("orderId", data.lId + "");
                        intent.putExtra("payType", "bucket");
                        startActivity(intent);
                    }
                });
                return orderListViewHolder;
            }
        });
        orderListAdapter.addAll(orderList);

//        orderListAdapter.addAll(orderList.subList(orderList.size() - orderListBean.result.dataList.size(), orderList.size()));
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        orderListAdapter.setMore(R.layout.view_more, this);
        orderListAdapter.setNoMore(R.layout.view_nomore);
    }

    int page = 1;

    @Override
    public void onLoadMore() {

        if (!userId.equals("")) {

            if (page < (nTotal / 8 + 1)) {
                page++;
                CloudApi.orderList(0x001, 1, 8 * page, Long.parseLong(userId), nState, myCallBack);
            } else {
                orderListAdapter.stopMore();
            }
        }
    }
}
