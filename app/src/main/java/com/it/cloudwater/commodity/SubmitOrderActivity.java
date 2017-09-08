package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.OrderDetailBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.pay.PayDetailActivity;
import com.it.cloudwater.user.AddressActivity;
import com.it.cloudwater.user.CouponActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 0x001;
    private static final int REQUEST_CODE2 = 0x002;
    private static final String TAG = "SubmitOrderActivity";
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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.img_water)
    ImageView imgWater;
    @BindView(R.id.tv_wanter_name)
    TextView tvWanterName;
    @BindView(R.id.tv_barrel_deposit)
    TextView tvBarrelDeposit;
    @BindView(R.id.unit_price)
    TextView unitPrice;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.total_order)
    TextView totalOrder;
    @BindView(R.id.tv_invoice_header)
    TextView tvInvoiceHeader;
    @BindView(R.id.et_invoice)
    EditText etInvoice;
    @BindView(R.id.rl_invoice_header)
    RelativeLayout rlInvoiceHeader;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.rl_remarks)
    RelativeLayout rlRemarks;
    @BindView(R.id.tv_ticket_use)
    TextView tvTicketUse;
    @BindView(R.id.ticket_count)
    TextView ticketCount;
    @BindView(R.id.rl_ticket_use)
    RelativeLayout rlTicketUse;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.rl_discount)
    RelativeLayout rlDiscount;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.total_pay)
    TextView totalPay;
    @BindView(R.id.btn_settlement)
    Button btnSettlement;

    private String order_Id;
    private int count_total = 0;
    private String userId;
    private OrderDetailBean orderDetailBean;
    private String addressId;
    private String strLocation;
    private String discount_amount;

    @Override
    protected void processLogic() {
        String addressName = StorageUtil.getValue(this, "address_name");
        String addressPhone = StorageUtil.getValue(this, "address_phone");
        String addressDetail = StorageUtil.getValue(this, "address_detail");
        if (!addressName.isEmpty()) {
            tvName.setText(addressName);
        }
        if (!addressPhone.isEmpty()) {
            tvPhone.setText(addressPhone);
        }
        if (!addressDetail.isEmpty()) {
            tvDetailAddress.setText(addressDetail);
        }
        CloudApi.orderPayDetail(0x001, Long.parseLong(order_Id), myCallBack);
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
                            orderDetailBean = new Gson().fromJson(body, OrderDetailBean.class);
                            tvWanterName.setText(orderDetailBean.result.orderGoods.get(0).strGoodsname);
                            tvBarrelDeposit.setText(((double) orderDetailBean.result.nBucketmoney / 100) + "元");
                            unitPrice.setText(((double) orderDetailBean.result.orderGoods.get(0).nPrice / 100) + "元");
                            count.setText(orderDetailBean.result.orderGoods.get(0).nCount + "");
                            totalOrder.setText(((double) orderDetailBean.result.orderGoods.get(0).nGoodsTotalPrice / 100) + "元");
                            tvDiscount.setText("-" + ((double) orderDetailBean.result.nCouponPrice / 100 + "元"));
                            totalPay.setText(((double) orderDetailBean.result.nFactPrice / 100) + "元");
                            ticketCount.setText(orderDetailBean.result.nTotalWatertickets + "");
                        } else if (resCode.equals("-1")) {
                            String resultData = jsonObject.getString("result");
                            ToastManager.show(resultData);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("结算完成，去付款吧");
                            String orderId = jsonObject.getString("result");
                            Intent intent = new Intent(SubmitOrderActivity.this, PayDetailActivity.class);
                            intent.putExtra("orderId", orderId + "");
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
        toolbarTitle.setText("提交订单");
        rlAddress.setOnClickListener(this);
        userId = StorageUtil.getUserId(this);
        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                String detailAddress = tvDetailAddress.getText().toString();
                String phone = tvPhone.getText().toString();
                String invoice = etInvoice.getText().toString();
                String remarks = etRemarks.getText().toString();
                HashMap<String, Object> orderGoodsParams = new HashMap<>();
                orderGoodsParams.put("lGId", orderDetailBean.result.orderGoods.get(0).lGId);
                orderGoodsParams.put("nGoodsFactPrice", orderDetailBean.result.orderGoods.get(0).nGoodsFactPrice);
                orderGoodsParams.put("nGoodsTotalPrice", orderDetailBean.result.orderGoods.get(0).nGoodsTotalPrice);
                orderGoodsParams.put("nWatertickets", orderDetailBean.result.orderGoods.get(0).nWatertickets);
                JSONObject orderGood = new JSONObject(orderGoodsParams);
                ArrayList<JSONObject> orderGoods = new ArrayList<>();
                orderGoods.add(orderGood);
                HashMap<String, Object> settlementParams = new HashMap<>();
                settlementParams.put("lId", orderDetailBean.result.lId);
                settlementParams.put("lBuyerid", userId);
                settlementParams.put("lAddressid", addressId);
                settlementParams.put("strReceiptusername", name);
                settlementParams.put("strLocation", strLocation);
                settlementParams.put("strReceiptmobile", phone);
                settlementParams.put("strDetailaddress", detailAddress);
                settlementParams.put("strInvoiceheader", invoice);
                settlementParams.put("strRemarks", remarks);
                if (discount_amount != null) {
                    settlementParams.put("nFactPrice", orderDetailBean.result.nFactPrice - Integer.parseInt(discount_amount) - orderDetailBean.result.nTotalWatertickets * orderDetailBean.result.nTotalWaterticketsPrice);
                    settlementParams.put("nCouponPrice", Integer.parseInt(discount_amount));
                    settlementParams.put("lMyCouponId", orderDetailBean.result.lMyCouponId);
                } else {
                    settlementParams.put("nFactPrice", orderDetailBean.result.nFactPrice - orderDetailBean.result.nTotalWatertickets * orderDetailBean.result.nTotalWaterticketsPrice);
                    settlementParams.put("nCouponPrice", 0);
                }
                settlementParams.put("nTotalprice", orderDetailBean.result.nTotalprice);


                settlementParams.put("orderGoods", orderGoods);
                JSONObject jsonObject = new JSONObject(settlementParams);
                CloudApi.settlement(0x002, jsonObject, myCallBack);

            }
        });
        rlDiscount.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_submit_order);
        order_Id = getIntent().getStringExtra("order_Id");
        Log.i(TAG, "processLogic: orderId" + order_Id);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_address:
                Intent intent = new Intent(SubmitOrderActivity.this, AddressActivity.class);
                intent.putExtra("address_tag", "address_tag");
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.rl_discount:
                startActivityForResult(new Intent(SubmitOrderActivity.this, CouponActivity.class), REQUEST_CODE2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && REQUEST_CODE == requestCode) {
            String addressName = data.getExtras().getString("addressName");

            addressId = data.getExtras().getString("addressId");
            strLocation = data.getExtras().getString("addressLocation");
            String addressPhone = data.getExtras().getString("addressPhone");
            String addressDetail = data.getExtras().getString("addressDetail");
            StorageUtil.setKeyValue(this, "address_name", addressName);
            StorageUtil.setKeyValue(this, "address_phone", addressPhone);
            StorageUtil.setKeyValue(this, "address_detail", addressDetail);
            tvDetailAddress.setText(addressDetail);
            tvName.setText(addressName);
            tvPhone.setText(addressPhone);
        }
        if (data != null && REQUEST_CODE2 == requestCode) {

            discount_amount = data.getExtras().getString("discount_amount");
            tvDiscount.setText(((double) Integer.parseInt(discount_amount) / 100) + "元");
            totalPay.setText(((double) (orderDetailBean.result.nFactPrice - Integer.parseInt(discount_amount)) / 100) + "元");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
