package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.it.cloudwater.widget.button.IOnAddDelListener;

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
    TextView count_num;
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
    private static final String TAG = "SubmitOrderActivity";
    private int count_total = 0;

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

        rlAddress.setOnClickListener(this);
        btnReplenish.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(final int count) {
                Log.i(TAG, "onAddSuccess: " + count);
                count_num.setText(count + "");

            }

            @Override
            public void onAddFailed(int count, FailType failType) {
                Log.i(TAG, "onAddFailed: count" + count);
                count_num.setText(count + "");
            }

            @Override
            public void onDelSuccess(int count) {
                count_num.setText(count + "");
                Log.i(TAG, "onDelSuccess: count" + count);
            }

            @Override
            public void onDelFaild(int count, FailType failType) {
                count_num.setText(count + "");
            }
        });

        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ---------" + count_num.getText());
                startActivity(new Intent(SubmitOrderActivity.this, PayActivity.class));
            }
        });
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
