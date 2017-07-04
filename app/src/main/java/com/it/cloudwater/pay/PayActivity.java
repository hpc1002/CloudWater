package com.it.cloudwater.pay;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.utils.ToastManager;

import butterknife.BindView;

public class PayActivity extends BaseActivity {


    @BindView(R.id.actual_pay)
    TextView actualPay;
    @BindView(R.id.iv_ali)
    ImageView ivAli;
    @BindView(R.id.rb_ali)
    RadioButton rbAli;
    @BindView(R.id.rl_ali_pay)
    RelativeLayout rlAliPay;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.rl_wechat_pay)
    RelativeLayout rlWechatPay;
    @BindView(R.id.btn_to_pay)
    Button btnToPay;
    private boolean byAli;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        rbAli.setClickable(false);
        rbWechat.setClickable(false);
        rbAli.setChecked(true);
        rbWechat.setChecked(false);
        byAli = true;
        Button mTo_pay = (Button) findViewById(R.id.btn_to_pay);

        rlAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbAli.setChecked(true);
                rbWechat.setChecked(false);
                byAli = true;
            }
        });
        rlWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbAli.setChecked(false);
                rbWechat.setChecked(true);
                byAli = false;
            }
        });
        mTo_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byAli) {
                    ToastManager.show("阿里支付");
                } else {
                    ToastManager.show("微信支付");
                }
                startActivity(new Intent(PayActivity.this, PayDetailActivity.class));
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_pay);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }


}
