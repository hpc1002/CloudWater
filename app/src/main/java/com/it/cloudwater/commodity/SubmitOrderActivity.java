package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.pay.PayActivity;

public class SubmitOrderActivity extends BaseActivity {

    private Button mSettlement;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

        mSettlement = (Button) findViewById(R.id.btn_settlement);
        mSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
