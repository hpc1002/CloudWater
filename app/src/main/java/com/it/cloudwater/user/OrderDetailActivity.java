package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.OrderDetailBean;
import com.it.cloudwater.commodity.SubmitOrderActivity;
import com.it.cloudwater.commodity.XuzhiActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.DateUtil;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.OrderViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_consignee)
    TextView tvConsignee;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.pay_total)
    TextView payTotal;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    @BindView(R.id.order_list_recycler)
    EasyRecyclerView orderListRecycler;
    @BindView(R.id.tag1)
    TextView tag1;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_complete_time)
    TextView tvCompleteTime;
    @BindView(R.id.sure_send)
    TextView sureSend;
    @BindView(R.id.tv_xuzhi)
    TextView tvXuzhi;
    private String orderId;
    private String order_sendState;
    private RecyclerArrayAdapter<OrderDetailBean.Result.OrderGoods> orderAdapter;
    private OrderDetailBean orderDetailBean;

    @Override
    protected void processLogic() {
        CloudApi.getOrderDetail(0x001, Long.parseLong(orderId), myCallBack);
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();

                    orderDetailBean = new Gson().fromJson(body, OrderDetailBean.class);
                    ArrayList<OrderDetailBean.Result.OrderGoods> orderGoodses = new ArrayList<>();
                    for (int i = 0; i < orderDetailBean.result.orderGoods.size(); i++) {
                        orderGoodses.add(orderDetailBean.result.orderGoods.get(i));
                    }
                    tvConsignee.setText(orderDetailBean.result.strReceiptusername);
                    tvPhone.setText(orderDetailBean.result.strReceiptmobile);
                    tvDetailAddress.setText(orderDetailBean.result.strDetailaddress);
                    tvTime.setText("下单时间: " + DateUtil.toDate(orderDetailBean.result.dtCreatetime));
                    tvPayTime.setText("支付时间: " + DateUtil.toDate(orderDetailBean.result.dtPaytime));
                    if ((orderDetailBean.result.dtFinishtime + "") != null) {
                        tvCompleteTime.setText("完成时间: " + DateUtil.toDate(orderDetailBean.result.dtFinishtime));
                    }

                    tvDeposit.setText("￥" + ((double) orderDetailBean.result.nBucketmoney / 100));
                    orderNumber.setText("订单号:" + orderDetailBean.result.strOrdernum);
                    couponCount.setText("使用优惠券" + orderDetailBean.result.nCouponPrice + "张");
                    payTotal.setText("￥" + ((double) orderDetailBean.result.nFactPrice / 100));
                    orderListRecycler.setAdapterWithProgress(orderAdapter = new RecyclerArrayAdapter<OrderDetailBean.Result.OrderGoods>(OrderDetailActivity.this) {
                        @Override
                        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                            return new OrderViewHolder(parent);
                        }
                    });
                    orderAdapter.addAll(orderGoodses);
                    break;
                case 0x002:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("已确认配送");
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

    @Override
    protected void setListener() {
        toolbarTitle.setText("订单详情");
        orderId = getIntent().getStringExtra("orderId");
        order_sendState = getIntent().getStringExtra("order_sendState");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        orderListRecycler.setLayoutManager(new LinearLayoutManager(this));
        if (Integer.parseInt(order_sendState) == 0) {
            sureSend.setVisibility(View.VISIBLE);
        } else {
            sureSend.setVisibility(View.GONE);
        }
        sureSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = StorageUtil.getUserId(OrderDetailActivity.this);
                String userName = StorageUtil.getValue(OrderDetailActivity.this, "userName");
                CloudApi.sendConfirm(0x002, Long.parseLong(orderId), Long.parseLong(userId), userName, myCallBack);
            }
        });
        tvXuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(OrderDetailActivity.this, XuzhiActivity.class);
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }


}
