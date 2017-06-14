package com.it.cloudwater;

import android.content.Context;

import com.it.cloudwater.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
