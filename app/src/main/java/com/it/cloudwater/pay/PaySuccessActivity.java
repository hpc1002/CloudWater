package com.it.cloudwater.pay;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.home.HomeActivity;
import com.it.cloudwater.user.OrderActivity;

import butterknife.BindView;

public class PaySuccessActivity extends BaseActivity {

    @BindView(R.id.order_price)
    TextView orderPrice;
    //    @BindView(R.id.order_name)
//    TextView orderName;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.look_order)
    TextView lookOrder;
    @BindView(R.id.return_home)
    TextView returnHome;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void processLogic() {
        TextPaint hotPaint = orderPrice.getPaint();
        hotPaint.setFakeBoldText(true);
//        String course_name = getIntent().getStringExtra("course_name");
        String total_amount = getIntent().getStringExtra("total_amount");
        String out_trade_no = getIntent().getStringExtra("out_trade_no");
        String timestamp = getIntent().getStringExtra("timestamp");
        String tag = getIntent().getStringExtra("tag");
        if (tag.equals("ali")) {
            orderPrice.setText(total_amount);
        } else if (tag.equals("wechat")) {
            orderPrice.setText(((double) Long.parseLong(total_amount) / 100) + "");
        }
        orderTime.setText(timestamp);
        orderNumber.setText(out_trade_no);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("支付结果");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaySuccessActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaySuccessActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_pay_success);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
