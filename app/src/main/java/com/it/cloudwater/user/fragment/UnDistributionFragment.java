package com.it.cloudwater.user.fragment;

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
 * Created by hpc on 2017/9/23.
 */

public class UnDistributionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    @BindView(R.id.erv_order_un_com)
    EasyRecyclerView ervOrderUnCom;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private View view;
    private String userId;
    private Integer nState = 0;
    private int nTotal;
    private RecyclerArrayAdapter<OrderListBean.Result.DataList> shopperAdapter;
    private ArrayList<OrderListBean.Result.DataList> dataLists;
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
            CloudApi.distributionList(0x001, 1, 8, Integer.parseInt(userId), nState, myCallBack);
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    mHandler.sendEmptyMessage(SWIPE_REFRESH_COMPLETE);
                    String body = result.body();

                    dataLists = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            OrderListBean orderListBean = new Gson().fromJson(body, OrderListBean.class);
                            nTotal = orderListBean.result.nTotal;

                            for (int i = 0; i < orderListBean.result.dataList.size(); i++) {
                                dataLists.add(orderListBean.result.dataList.get(i));
                            }
                            initUi(dataLists);
                        } else if (resCode.equals("1")) {
                            ToastManager.show("暂无配送单");
                            initUi(dataLists);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 0x002:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("已确认配送");
                            CloudApi.distributionList(0x001, 1, 8, Long.parseLong(userId), nState, myCallBack);
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

    private void initUi(final ArrayList<OrderListBean.Result.DataList> dataLists) {
        ervOrderUnCom.setAdapterWithProgress(shopperAdapter = new RecyclerArrayAdapter<OrderListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                return new ShopperListViewHolder(parent);
                OrderListViewHolder orderListViewHolder = new OrderListViewHolder(parent, getActivity(), "distributionState");
                orderListViewHolder.setCallBack(new OrderListViewHolder.allCheck() {
                    @Override
                    public void OnItemClickListener(OrderListBean.Result.DataList data) {

                    }

                    @Override
                    public void OnToSettleClickListener(OrderListBean.Result.DataList data) {

                    }

                    @Override
                    public void OnDistributionItemClickListener(OrderListBean.Result.DataList data) {
                        String userId = StorageUtil.getUserId(getActivity());
                        String userName = StorageUtil.getValue(getActivity(), "userName");
                        CloudApi.sendConfirm(0x002, data.lId, Long.parseLong(userId), userName, myCallBack);
                    }

                    @Override
                    public void OnItemDeleteClickListener(OrderListBean.Result.DataList data) {

                    }
                });
                return orderListViewHolder;
            }

        });
        shopperAdapter.addAll(dataLists);
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        shopperAdapter.setMore(R.layout.view_more, this);
        shopperAdapter.setNoMore(R.layout.view_nomore);
    }


    @Override
    public void onRefresh() {
        if (!userId.equals("")) {
            CloudApi.distributionList(0x001, 1, 8, Integer.parseInt(userId), nState, myCallBack);
        }
    }

    int page = 1;

    @Override
    public void onLoadMore() {
        if (!userId.equals("")) {

            if (page < (nTotal / 8 + 1)) {
                page++;
                CloudApi.distributionList(0x001, 1, 8 * page, Long.parseLong(userId), nState, myCallBack);
            } else {
                shopperAdapter.stopMore();
            }
        }
    }
}
