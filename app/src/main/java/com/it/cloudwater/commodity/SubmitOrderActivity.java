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
import com.it.cloudwater.pay.PayActivity;
import com.it.cloudwater.user.AddressActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 0x001;
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

    @Override
    protected void processLogic() {
        String address = StorageUtil.getValue(this, "address");
        if (!address.isEmpty()) {
            tvDetailAddress.setText(address);
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
                            ArrayList<OrderDetailBean.Result> goodsData = new ArrayList<>();
                            OrderDetailBean orderDetailBean = new Gson().fromJson(body, OrderDetailBean.class);
                            tvWanterName.setText(orderDetailBean.result.orderGoods.get(0).strGoodsname);
                            tvBarrelDeposit.setText(((double) orderDetailBean.result.nBucketmoney / 100) + "");
                            unitPrice.setText(((double) orderDetailBean.result.orderGoods.get(0).nPrice / 100) + "");
                            count.setText(orderDetailBean.result.orderGoods.get(0).nCount + "");
                            totalOrder.setText(((double) orderDetailBean.result.orderGoods.get(0).nGoodsTotalPrice / 100) + "");
                            tvDiscount.setText("-" + ((double) orderDetailBean.result.nCouponPrice / 100));
                            totalPay.setText(((double) orderDetailBean.result.nFactPrice / 100) + "");
                        } else if (resCode.equals("-1")) {
                            String resultData = jsonObject.getString("result");
                            ToastManager.show(resultData);
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
        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmitOrderActivity.this, PayActivity.class));
            }
        });
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
                startActivityForResult(new Intent(SubmitOrderActivity.this, AddressActivity.class), REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && REQUEST_CODE == requestCode) {
            String result = data.getExtras().getString("result");
            StorageUtil.setKeyValue(this, "address", result);
            tvDetailAddress.setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
