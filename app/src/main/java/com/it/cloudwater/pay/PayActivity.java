package com.it.cloudwater.pay;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;

public class PayActivity extends BaseActivity {


    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        Button mTo_pay = (Button) findViewById(R.id.btn_to_pay);
        mTo_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayActivity.this,PayDetailActivity.class));
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
