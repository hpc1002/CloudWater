package com.it.cloudwater.pay;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.OrderDetailBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.DateUtil;
import com.it.cloudwater.viewholder.OrderViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class PayDetailActivity extends BaseActivity {

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
    //    @BindView(R.id.iv_bucket)
//    ImageView ivBucket;
//    @BindView(R.id.tv_water_name)
//    TextView tvWaterName;
//    @BindView(R.id.tv_number)
//    TextView tvNumber;
//    @BindView(R.id.tv_price)
//    TextView tvPrice;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.pay_total)
    TextView payTotal;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.order_list_recycler)
    EasyRecyclerView orderListRecycler;
    private String orderId;
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
                    tvDeposit.setText("￥" + ((double) orderDetailBean.result.nBucketmoney * orderDetailBean.result.nBucketnum / 100));
                    orderNumber.setText("订单号:" + orderDetailBean.result.strOrdernum);
                    couponCount.setText("使用优惠券" + orderDetailBean.result.nCouponPrice + "张");
                    payTotal.setText("￥" + ((double) orderDetailBean.result.nFactPrice / 100));
                    orderListRecycler.setAdapterWithProgress(orderAdapter = new RecyclerArrayAdapter<OrderDetailBean.Result.OrderGoods>(PayDetailActivity.this) {
                        @Override
                        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                            return new OrderViewHolder(parent);
                        }
                    });
                    orderAdapter.addAll(orderGoodses);
                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            Intent intent = new Intent(PayDetailActivity.this, PaySuccessActivity.class);
                            intent.putExtra("out_trade_no", orderDetailBean.result.strOrdernum);

                            intent.putExtra("timestamp", DateUtil.toDate(orderDetailBean.result.dtPaytime));
                            intent.putExtra("total_amount", ((double) orderDetailBean.result.nFactPrice / 100) + "");
                            startActivity(intent);
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
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDetailBean.result.nFactPrice == 0 && orderDetailBean.result.nTotalWatertickets != 0) {
                    CloudApi.setPayNstate(0x002, Long.parseLong(orderId), 3, myCallBack);
                } else {
                    Intent intent = new Intent(PayDetailActivity.this, PayActivity.class);
                    intent.putExtra("orderId", orderId + "");
                    intent.putExtra("payType", "bucket");
                    startActivity(intent);
                }
            }
        });
        orderListRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_pay_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
