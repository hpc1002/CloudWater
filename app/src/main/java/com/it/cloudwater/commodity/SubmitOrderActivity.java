package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.pay.PayActivity;
import com.it.cloudwater.user.AddressActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.widget.button.AnimShopButton;

import butterknife.BindView;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.unit_price)
    TextView unitPrice;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.btnReplenish)
    AnimShopButton btnReplenish;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.total_order)
    TextView totalOrder;
    @BindView(R.id.tv_invoice_header)
    TextView tvInvoiceHeader;
    @BindView(R.id.rl_invoice_header)
    RelativeLayout rlInvoiceHeader;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.rl_remarks)
    RelativeLayout rlRemarks;
    @BindView(R.id.tv_ticket_use)
    TextView tvTicketUse;
    @BindView(R.id.rl_ticket_use)
    RelativeLayout rlTicketUse;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.rl_discount)
    RelativeLayout rlDiscount;
    @BindView(R.id.btn_settlement)
    Button btnSettlement;
    private static final int REQUEST_CODE = 0x001;

    @Override
    protected void processLogic() {
        String address = StorageUtil.getValue(this, "address");
        if (!address.isEmpty()) {
            tvAddress.setText(address);
        }
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("提交订单");
        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmitOrderActivity.this, PayActivity.class));
            }
        });
        rlAddress.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_submit_order);
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
            tvAddress.setText(result);
        }
    }
}
