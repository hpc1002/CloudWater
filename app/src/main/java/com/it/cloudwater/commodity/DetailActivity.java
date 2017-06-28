package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        Button mOrdersSubmit = (Button) findViewById(R.id.order_submit);
        mOrdersSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, SubmitOrderActivity.class));
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
