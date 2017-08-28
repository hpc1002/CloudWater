package com.it.cloudwater.pay;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.pay_status)
    TextView payStatus;
    @BindView(R.id.iv_bucket)
    ImageView ivBucket;
    @BindView(R.id.tv_water_name)
    TextView tvWaterName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.pay_total)
    TextView payTotal;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    private String orderId;

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
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            JSONObject orderDetail = jsonObject.getJSONObject("result");
                            JSONObject orderResult = new JSONObject(orderDetail.toString());
                            String strOrdernum = orderDetail.getString("strOrdernum");
                            String strBuyername = orderDetail.getString("strBuyername");
                            String strReceiptusername = orderDetail.getString("strReceiptusername");
                            String strReceiptmobile = orderDetail.getString("strReceiptmobile");
                            String strLocation = orderDetail.getString("strLocation");
                            String strDetailaddress = orderDetail.getString("strDetailaddress");
                            String strInvoiceheader = orderDetail.getString("strInvoiceheader");
                            long lId = orderDetail.getLong("lId");
                            int nState = orderDetail.getInt("nState");
                            int lBuyerid = orderDetail.getInt("lBuyerid");
                            int lAddressid = orderDetail.getInt("lAddressid");
                            int nBucketnum = orderDetail.getInt("nBucketnum");
                            int nBucketmoney = orderDetail.getInt("nBucketmoney");
                            int nCouponPrice = orderDetail.getInt("nCouponPrice");
                            int lMyCouponId = orderDetail.getInt("lMyCouponId");
                            int nFactPrice = orderDetail.getInt("nFactPrice");
                            int nTotalprice = orderDetail.getInt("nTotalprice");
                            long dtCreatetime = orderDetail.getLong("dtCreatetime");
                            long dtPaytime = orderDetail.getLong("dtPaytime");
                            JSONArray orderGoods = orderDetail.getJSONArray("orderGoods");
                            JSONObject good = new JSONObject(orderGoods.get(0).toString());
                            int lGId = good.getInt("lGId");
                            int lOrderid = good.getInt("lOrderid");
                            int lGoodsid = good.getInt("lGoodsid");
                            int nPrice = good.getInt("nPrice");
                            int nCount = good.getInt("nCount");
                            int nGoodsFactPrice = good.getInt("nGoodsFactPrice");
                            int nGoodsTotalPrice = good.getInt("nGoodsTotalPrice");
                            int nWatertickets = good.getInt("nWatertickets");
                            String strGoodsname = good.getString("strGoodsname");
                            String strGoodsimgurl = good.getString("strGoodsimgurl");
                            tvConsignee.setText(strReceiptusername);
                            tvPhone.setText(strReceiptmobile);
                            tvDetailAddress.setText(strDetailaddress);
                            tvTime.setText(dtCreatetime+"");
                            orderNumber.setText(strOrdernum+"");
                            tvNumber.setText(nCount+"");
                            tvWaterName.setText(strGoodsname);
                            tvPrice.setText("￥"+((double) nPrice / 100) + "元");
                            tvDeposit.setText("￥"+((double) nBucketmoney / 100) + "元");
                            couponCount.setText(1+"");
                            payTotal.setText("￥"+((double) nFactPrice / 100) + "元");
                            if (nState==2){
                                payStatus.setText("未支付");
                            }else if (nState==1){
                                payStatus.setText("已支付");
                            }
                        } else {
                            ToastManager.show("返回错误");
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
